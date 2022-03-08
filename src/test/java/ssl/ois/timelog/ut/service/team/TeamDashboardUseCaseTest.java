package ssl.ois.timelog.ut.service.team;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.adapter.manager.memory.MemoryAMSManager;
import ssl.ois.timelog.adapter.presenter.log.dash.board.TeamDashBoardPresenter;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUnitRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.service.team.Person;
import ssl.ois.timelog.service.team.dashboard.TeamDashboardUseCase;
import ssl.ois.timelog.service.team.dashboard.TeamDashboardUseCaseInput;
import ssl.ois.timelog.service.team.dashboard.TeamDashboardUseCaseOutput;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TeamDashboardUseCaseTest {
    AccountManager accountManager;
    LogRepository logRepository;
    UnitRepository unitRepository;

    String dummyUserName = "Mashu";
    String dummyTeamName = "OIS";
    Unit dummyUser;
    Unit dummyTeam;

    private void createTeam() {
        List<ActivityType> activityTypeList = new ArrayList<>();
        activityTypeList.add(new ActivityType(UUID.fromString("11111111-0000-0000-0000-000000000001"), "team activity type 1", true, true));
        activityTypeList.add(new ActivityType(UUID.fromString("11111111-0000-0000-0000-000000000002"), "team activity type 2", true, true));
        Map<UUID, Role> roleMap = new HashMap<>();
        roleMap.put(this.dummyUser.getID(), Role.LEADER);
        this.dummyTeam = new Team(UUID.fromString("00000000-0000-0000-0000-000000000001"), activityTypeList, roleMap);

        try {
            this.unitRepository.addActivityType(this.dummyTeam);
        } catch (DuplicateActivityTypeException | DatabaseErrorException e) {
            fail("failed to add activity type");
        }
    }

    private void createUser() {
        this.dummyUser = new User(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        try {
            this.dummyUser.addActivityType(new ActivityType(
                    UUID.fromString("11111111-0000-0000-0000-000000000000"),
                    "dummy activity type",
                    true,
                    true
            ));

            this.unitRepository.addActivityType(this.dummyUser);
        } catch (DuplicateActivityTypeException | DatabaseErrorException e) {
            fail("failed to create activity type for user");
        }
    }

    private void userAddLogOnTeam() {
        UUID activityUserMapperId = null;
        try {
            activityUserMapperId = this.unitRepository.findActivityUserMapperID(this.dummyTeam.getID().toString(), "team activity type 1");
        } catch (DatabaseErrorException e) {
            fail("failed to find activity type");
        }

        try {
            this.logRepository.addLog(new Log(
                    UUID.fromString("22222222-0000-0000-0000-000000000000"),
                    this.dummyUser.getID(),
                    "dummy log title",
                    "2021/12/31 17:00",
                    "2021/12/31 19:00",
                    "dummy log description",
                    "dummy activity type name",
                    activityUserMapperId
            ));
        } catch (SaveLogErrorException e) {
            fail("failed to add log");
        }
    }

    @Before
    public void setup() {
        this.unitRepository = new MemoryUnitRepository();
        this.logRepository = new MemoryLogRepository(this.unitRepository);

        this.createUser();
        this.createTeam();
        this.userAddLogOnTeam();

        Map<String, Unit> userMap = new HashMap<>();
        userMap.put(this.dummyUserName, this.dummyUser);
        userMap.put(this.dummyTeamName, this.dummyTeam);
        this.accountManager = new MemoryAMSManager(userMap);
    }

    @Test
    public void test_get_team_dashboard_with_one_user_and_one_log() {
        TeamDashboardUseCase uc = new TeamDashboardUseCase(this.logRepository, this.accountManager, this.unitRepository);
        TeamDashboardUseCaseInput input = new TeamDashboardUseCaseInput();
        List<Person> members = new ArrayList<>();
        members.add(new Person(this.dummyUserName, "",  this.dummyUser.getID()));
        input.setStartDate("2021/12/31 00:00");
        input.setEndDate("2021/12/31 23:59");
        input.setTeamID(this.dummyTeam.getID().toString());
        input.setPersonal(false);
        input.setFilterList(null);
        input.setMemberList(members.stream().map(Person::getUsername).collect(Collectors.toList()));

        TeamDashboardUseCaseOutput output = new TeamDashBoardPresenter();
        try {
            uc.execute(input, output);
        } catch (Exception e) {
            fail("failed to execute usecase");
        }

        assertEquals(1, output.getTeamLogDTOList().size());
        assertEquals("22222222-0000-0000-0000-000000000000", output.getTeamLogDTOList().get(0).getId());
        assertEquals("dummy log title", output.getTeamLogDTOList().get(0).getTitle());
    }

    @Test
    public void test_get_dashboard_with_empty_time_interval() {
        TeamDashboardUseCase uc = new TeamDashboardUseCase(this.logRepository, this.accountManager, this.unitRepository);
        TeamDashboardUseCaseInput input = new TeamDashboardUseCaseInput();
        List<Person> members = new ArrayList<>();
        members.add(new Person(this.dummyUserName, "", this.dummyUser.getID()));
        input.setStartDate("2021/12/01 00:00");
        input.setEndDate("2021/12/20 23:59");
        input.setTeamID(this.dummyTeam.getID().toString());
        input.setPersonal(false);
        input.setFilterList(null);
        input.setMemberList(members.stream().map(Person::getUsername).collect(Collectors.toList()));

        TeamDashboardUseCaseOutput output = new TeamDashBoardPresenter();
        try {
            uc.execute(input, output);
        } catch (Exception e) {
            fail("failed to execute usecase");
        }

        assertEquals(0, output.getTeamLogDTOList().size());
    }

    @Test
    public void test_get_team_dashboard_containing_personal_log() {
        TeamDashboardUseCase uc = new TeamDashboardUseCase(this.logRepository, this.accountManager, this.unitRepository);
        TeamDashboardUseCaseInput input = new TeamDashboardUseCaseInput();
        List<Person> members = new ArrayList<>();
        members.add(new Person(this.dummyUserName, "", this.dummyUser.getID()));
        input.setStartDate("2021/12/31 00:00");
        input.setEndDate("2021/12/31 23:59");
        input.setTeamID(this.dummyTeam.getID().toString());
        input.setPersonal(true);
        input.setFilterList(null);
        input.setMemberList(members.stream().map(Person::getUsername).collect(Collectors.toList()));

        TeamDashboardUseCaseOutput output = new TeamDashBoardPresenter();
        try {
            uc.execute(input, output);
        } catch (Exception e) {
            fail("failed to execute usecase");
        }
        System.out.println(output.getMemberDashboardMap());

        assertEquals(1, output.getTeamLogDTOList().size());
        assertEquals("22222222-0000-0000-0000-000000000000", output.getTeamLogDTOList().get(0).getId());
        assertEquals("dummy log title", output.getTeamLogDTOList().get(0).getTitle());

        assertEquals("22222222-0000-0000-0000-000000000000", output.getMemberDashboardMap().get("Mashu").get(0).getId());
        assertEquals("dummy log title", output.getMemberDashboardMap().get("Mashu").get(0).getTitle());
    }
}

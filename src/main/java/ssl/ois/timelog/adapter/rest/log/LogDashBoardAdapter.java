package ssl.ois.timelog.adapter.rest.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssl.ois.timelog.adapter.presenter.log.dash.board.LogDashBoardPresenter;
import ssl.ois.timelog.adapter.presenter.log.dash.board.TeamDashBoardPresenter;
import ssl.ois.timelog.adapter.view.model.log.dash.board.LogDashBoardViewModel;
import ssl.ois.timelog.adapter.view.model.log.dash.board.TeamDashBoardViewModel;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.log.history.HistoryLogUseCase;
import ssl.ois.timelog.service.log.history.HistoryLogUseCaseInput;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.service.team.dashboard.TeamDashboardUseCase;
import ssl.ois.timelog.service.team.dashboard.TeamDashboardUseCaseInput;

import java.text.ParseException;

@RestController
@RequestMapping("/api/dash-board/")
public class LogDashBoardAdapter {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private AccountManager accountManager;

    @PostMapping("/spent-time")
    public ResponseEntity<LogDashBoardViewModel> viewLogDashBoard(@RequestBody HistoryLogUseCaseInput input) {
        HistoryLogUseCase useCase = new HistoryLogUseCase(logRepository, unitRepository, accountManager);

        LogDashBoardPresenter presenter = new LogDashBoardPresenter();

        try {
            useCase.execute(input, presenter);
            return ResponseEntity.status(HttpStatus.OK).body(presenter.build());
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LogDashBoardViewModel());
        } catch (DatabaseErrorException | AccountErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LogDashBoardViewModel());
        }
    }

    @PostMapping("/team/dashboard")
    public ResponseEntity<TeamDashBoardViewModel> viewTeamDashBoard(@RequestBody TeamDashboardUseCaseInput input) {
        TeamDashboardUseCase useCase = new TeamDashboardUseCase(this.logRepository, this.accountManager, this.unitRepository);
        TeamDashBoardPresenter presenter = new TeamDashBoardPresenter();

        try {
            useCase.execute(input, presenter);
            return ResponseEntity.status(HttpStatus.OK).body(presenter.build());
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TeamDashBoardViewModel());
        } catch (DatabaseErrorException | AccountErrorException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TeamDashBoardViewModel());
        }
    }
}

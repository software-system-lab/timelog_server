package ssl.ois.timelog.adapter.presenter.log.dash.board;

import ssl.ois.timelog.adapter.view.model.log.dash.board.TeamDashBoardViewModel;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.team.dashboard.TeamDashboardUseCaseOutputBound;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TeamDashBoardPresenter extends TeamDashboardUseCaseOutputBound {
    public TeamDashBoardViewModel build() throws ParseException {

        TeamDashBoardViewModel viewModel = new TeamDashBoardViewModel();
        long totalTime = 0;
        for (LogDTO log : this.getTeamLogDTOList()) {
            TeamDashBoardViewModel.Data data = new TeamDashBoardViewModel.Data();
            SimpleDateFormat dateFormat = new SimpleDateFormat(Log.DATE_FORMAT);
            Date startTime = dateFormat.parse(log.getStartTime());
            Date endTime = dateFormat.parse(log.getEndTime());

            long timeLength = endTime.getTime() - startTime.getTime();
            data.setTimeLength(timeLength/60000);
            totalTime += timeLength;

            int hour = this.getHour(timeLength);
            int minute = this.getMinute(timeLength);

            data.setHour(hour);
            data.setMinute(minute);

            viewModel.add(log.getActivityTypeName(), data);
        }

        int hour = this.getHour(totalTime);
        int minute = this.getMinute(totalTime);
        viewModel.setTotalTime(String.format("%02d:%02d", hour, minute));

        for (Map.Entry<String, List<LogDTO>> entry : this.getMemberdashboardMap().entrySet()){
            TeamDashBoardViewModel.MemberDashboard memberDashboard = new TeamDashBoardViewModel.MemberDashboard(entry.getKey());
            long memberTotalTime = 0;
            for (LogDTO log : entry.getValue()){
                TeamDashBoardViewModel.Data data = new TeamDashBoardViewModel.Data();
                SimpleDateFormat dateFormat = new SimpleDateFormat(Log.DATE_FORMAT);
                Date startTime = dateFormat.parse(log.getStartTime());
                Date endTime = dateFormat.parse(log.getEndTime());
    
                long timeLength = endTime.getTime() - startTime.getTime();
                data.setTimeLength(timeLength/60000);
                memberTotalTime += timeLength;
    
                int memberHour = this.getHour(timeLength);
                int memberMinute = this.getMinute(timeLength);
    
                data.setHour(memberHour);
                data.setMinute(memberMinute);

                memberDashboard.add(log.getActivityTypeName(), data);
            }
            int memberHour = this.getHour(memberTotalTime);
            int memberMinute = this.getMinute(memberTotalTime);
            memberDashboard.setTotalTime(String.format("%02d:%02d", memberHour, memberMinute));

            viewModel.addMember(memberDashboard);
        }
       
        return viewModel;
    }

    private int getHour(long timestamp) {
        return (int)(timestamp / (1000 * 60 * 60));
    }

    private int getMinute(long timestamp) {
        return (int)(timestamp / (1000 * 60) % 60);
    }
}

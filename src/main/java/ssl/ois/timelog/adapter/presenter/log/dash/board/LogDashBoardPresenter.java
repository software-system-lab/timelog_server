package ssl.ois.timelog.adapter.presenter.log.dash.board;

import ssl.ois.timelog.adapter.view.model.log.dash.board.LogDashBoardViewModel;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.log.history.HistoryLogUseCaseOutputBound;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogDashBoardPresenter extends HistoryLogUseCaseOutputBound {
    public LogDashBoardViewModel build() {
        LogDashBoardViewModel viewModel = new LogDashBoardViewModel();
        long totalTime = 0;
        try {
            for (LogDTO log : this.getLogDTOList()) {
                LogDashBoardViewModel.Data data = new LogDashBoardViewModel.Data();
                SimpleDateFormat dateFormat = new SimpleDateFormat(Log.dateFormatString);
                Date startTime = dateFormat.parse(log.getStartTime());
                Date endTime = dateFormat.parse(log.getEndTime());

                long timeLength = endTime.getTime() - startTime.getTime();
                data.setTimeLength(timeLength);
                totalTime += timeLength;

                int hour = this.getHour(timeLength);
                int minute = this.getMinute(timeLength);

                data.setHour(hour);
                data.setMinute(minute);
                viewModel.add(log.getActivityTypeName(), data);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        int hour = this.getHour(totalTime);
        int minute = this.getMinute(totalTime);
        viewModel.setTotalTime(String.format("%02d:%02d", hour, minute));

        return viewModel;
    }

    private int getHour(long timestamp) {
        return (int)(timestamp / (1000 * 60 * 60));
    }

    private int getMinute(long timestamp) {
        return (int)(timestamp / (1000 * 60) % 60);
    }
}
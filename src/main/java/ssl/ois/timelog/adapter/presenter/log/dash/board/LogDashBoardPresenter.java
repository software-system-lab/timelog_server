package ssl.ois.timelog.adapter.presenter.log.dash.board;

import ssl.ois.timelog.adapter.view.model.log.dash.board.LogDashBoardViewModel;
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
        List<LogDashBoardViewModel.Data> dataList = new ArrayList<>();
        try {
            for (LogDTO log : this.getLogDTOList()) {
                LogDashBoardViewModel.Data data = new LogDashBoardViewModel.Data();

                data.setActivityTypeName(log.getActivityTypeName());

                Date startTime = LogDTO.dateFormat.parse(log.getStartTime());
                Date endTime = LogDTO.dateFormat.parse(log.getEndTime());

                long timeLength = endTime.getTime() - startTime.getTime();
                data.setTimeLength(timeLength);
                totalTime += timeLength;

                int hour = (int)(timeLength / (1000 * 60 * 60));
                int minute = (int)(timeLength / (1000 * 60) % 60);

                data.setHour(hour);
                data.setMinute(minute);
                dataList.add(data);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

        viewModel.setTotalTime(hourFormat.format(new Date(totalTime)));
        viewModel.setData(dataList);

        return viewModel;
    }
}

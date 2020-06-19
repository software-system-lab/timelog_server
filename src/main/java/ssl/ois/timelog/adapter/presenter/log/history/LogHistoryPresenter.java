package ssl.ois.timelog.adapter.presenter.log.history;

import ssl.ois.timelog.adapter.view.model.log.history.LogHistoryViewModel;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.log.history.HistoryLogUseCaseOutput;

import java.util.ArrayList;
import java.util.List;

public class LogHistoryPresenter implements HistoryLogUseCaseOutput {
    private List<LogDTO> logDTOList;

    public LogHistoryPresenter() {
        this.logDTOList = new ArrayList<>();
    }

    public List<LogDTO> getLogDTOList() {
        return logDTOList;
    }

    public void setLogDTOList(List<LogDTO> logDTOList) {
        this.logDTOList = logDTOList;
    }

    public LogHistoryViewModel build() {
        LogHistoryViewModel viewModel = new LogHistoryViewModel();
        for (LogDTO log: this.logDTOList) {
            LogHistoryViewModel.LogItem logItem = new LogHistoryViewModel.LogItem();
            logItem.setActivityTypeName(log.getActivityTypeName());
            logItem.setTitle(log.getTitle());
            logItem.setStartTime(log.getStartTime());
            logItem.setEndTime(log.getEndTime());
            viewModel.addItem(logItem);
        }
        return viewModel;
    }
}

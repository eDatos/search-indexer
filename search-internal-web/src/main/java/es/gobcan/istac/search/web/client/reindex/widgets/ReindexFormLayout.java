package es.gobcan.istac.search.web.client.reindex.widgets;

import org.siemac.metamac.web.common.client.widgets.InformationLabel;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.ViewMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.ds.ReindexDS;

public class ReindexFormLayout extends ViewMainFormLayout {

    ToolStripButton      reindexStartButton;
    private ViewTextItem cronExpression;
    private ViewTextItem statusKey;
    private ViewTextItem lastExecutionDateSinceReboot;

    ReindexFormLayout() {
        super();
    }

    ReindexFormLayout(String title, String description) {
        super();

        setTitleLabelContents(title);

        reindexStartButton = new ToolStripButton(SearchWeb.getConstants().start());
        getToolStrip().addButton(reindexStartButton);

        if (description != null) {
            addViewCanvas(new InformationLabel(description));
        }

        CustomDynamicForm subpanel = new CustomDynamicForm();
        subpanel.setColWidths("30%", "*");

        cronExpression = new ViewTextItem(ReindexDS.CRON_EXPRESSION, SearchWeb.getConstants().cronExpression());
        statusKey = new ViewTextItem(ReindexDS.STATUS, SearchWeb.getConstants().status());
        lastExecutionDateSinceReboot = new ViewTextItem(ReindexDS.LAST_EXECUTION_SINCE_REBOOT, SearchWeb.getConstants().lastExecutionSinceServerReboot());
        subpanel.addFields(cronExpression, statusKey, lastExecutionDateSinceReboot);

        addViewCanvas(subpanel);
    }

    public ToolStripButton getReindexStartButton() {
        return reindexStartButton;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression.setValue(cronExpression);
    }

    public void setStatusKey(String status) {
        statusKey.setValue(status);
    }

    public String getStatus() {
        return (String) statusKey.getValue();
    }

    public void setLastExecutionSinceReboot(String lastExecutionSinceReboot) {
        lastExecutionDateSinceReboot.setValue(lastExecutionSinceReboot);
    }

    public void setIndexationStatus(IndexationStatusDto indexationStatus) {
        setStatusKey(indexationStatus.getStatusKey());
        setLastExecutionSinceReboot(indexationStatus.getLastExecutionDate());
    }

}

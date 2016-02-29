package es.gobcan.istac.search.web.client.reindex.widgets;

import org.siemac.metamac.web.common.client.widgets.InformationLabel;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.ViewMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.ds.ReindexDS;

public class ReindexFormLayout extends ViewMainFormLayout {

    ToolStripButton      reindexStartButton;
    private ViewTextItem cronExpression;
    private ViewTextItem status;
    private ViewTextItem lastExecutionSinceReboot;

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
        status = new ViewTextItem(ReindexDS.STATUS, SearchWeb.getConstants().status());
        lastExecutionSinceReboot = new ViewTextItem(ReindexDS.LAST_EXECUTION_SINCE_REBOOT, SearchWeb.getConstants().lastExecutionSinceServerReboot());
        subpanel.addFields(cronExpression, status, lastExecutionSinceReboot);

        addViewCanvas(subpanel);
    }

    public ToolStripButton getReindexStartButton() {
        return reindexStartButton;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression.setValue(cronExpression);
    }

    public void setStatus(String status) {
        this.status.setValue(status);
    }

    public void setLastExecutionSinceReboot(String lastExecutionSinceReboot) {
        this.lastExecutionSinceReboot.setValue(lastExecutionSinceReboot);
    }

}

package es.gobcan.istac.search.web.client.reindex.widgets;

import org.siemac.metamac.web.common.client.widgets.InformationLabel;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.ViewMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.ds.ReindexDS;
import es.gobcan.istac.search.web.client.utils.CoreMessagesUtils;

public class ReindexFormLayout extends ViewMainFormLayout {

    ToolStripButton      reindexStartButton;
    private ViewTextItem cronExpression;
    private ViewTextItem statusKey;
    private ViewTextItem statusValue;
    private ViewTextItem lastExecutionDateSinceReboot;
    private Widget       widgetPanel;

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
        statusKey = new ViewTextItem(ReindexDS.STATUS_KEY, "");
        statusKey.setVisible(false);
        statusValue = new ViewTextItem(ReindexDS.STATUS_VALUE, SearchWeb.getConstants().status());
        lastExecutionDateSinceReboot = new ViewTextItem(ReindexDS.LAST_EXECUTION_SINCE_REBOOT, SearchWeb.getConstants().lastExecutionSinceServerReboot());
        subpanel.addFields(cronExpression, statusKey, statusValue, lastExecutionDateSinceReboot);

        addViewCanvas(subpanel);
    }

    public ToolStripButton getReindexStartButton() {
        return reindexStartButton;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression.setValue(cronExpression);
    }

    public void hideCronExpression() {
        cronExpression.setVisible(false);
    }

    public void setStatus(String statusKey) {
        this.statusKey.setValue(statusKey);
        if (statusKey != null) {
            statusValue.setValue(CoreMessagesUtils.getCoreMessage(IndexacionStatusDomain.valueOf(statusKey).getDescripcion()));
        }
    }

    public String getStatusKey() {
        return (String) statusKey.getValue();
    }

    public void setLastExecutionSinceReboot(String lastExecutionSinceReboot) {
        lastExecutionDateSinceReboot.setValue(lastExecutionSinceReboot);
    }

    public void setIndexationStatus(IndexationStatusDto indexationStatus) {
        setStatus(indexationStatus.getStatusKey());
        setLastExecutionSinceReboot(indexationStatus.getLastExecutionDate());
    }

}

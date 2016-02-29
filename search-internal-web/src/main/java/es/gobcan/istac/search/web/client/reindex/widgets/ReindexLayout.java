package es.gobcan.istac.search.web.client.reindex.widgets;

import org.siemac.metamac.web.common.client.widgets.InformationLabel;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.ViewMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.ds.ReindexDS;

public class ReindexLayout extends VLayout {

    private ViewMainFormLayout reindexGeneralForm;
    private ViewMainFormLayout reindexGpe;
    private ViewMainFormLayout reindexWeb;

    private ViewTextItem       gpeCronExpression;
    private ViewTextItem       webCronExpression;
    private ViewTextItem       gpeStatus;
    private ViewTextItem       gpeLastExecutionSinceReboot;
    private ViewTextItem       webStatus;
    private ViewTextItem       webLastExecutionSinceReboot;
    private ToolStripButton    reindexGpeStartButton;
    private ToolStripButton    reindexWebStartButton;

    public ReindexLayout() {
        createReindexGeneralForm();
        createReindexGpe();
        createReindexWeb();

        addMember(reindexGeneralForm);
        addMember(reindexGpe);
        addMember(reindexWeb);
    }

    private void createReindexGeneralForm() {
        reindexGeneralForm = new ViewMainFormLayout();
        reindexGeneralForm.setTitleLabelContents(SearchWeb.getConstants().reindexGeneral());

        reindexGeneralForm.getToolStrip().hide();

        reindexGeneralForm.addViewCanvas(new InformationLabel(SearchWeb.getConstants().reindexGeneralDescription()));

        CustomDynamicForm subpanel = createSubPanel();
        gpeCronExpression = new ViewTextItem(ReindexDS.GPE_CRON_EXPRESSION, SearchWeb.getConstants().reindexGpeCron());
        webCronExpression = new ViewTextItem(ReindexDS.WEB_CRON_EXPRESSION, SearchWeb.getConstants().reindexWebCron());
        subpanel.addFields(gpeCronExpression, webCronExpression);

        reindexGeneralForm.addViewCanvas(subpanel);
    }

    private void createReindexGpe() {
        reindexGpe = new ViewMainFormLayout();
        reindexGpe.setTitleLabelContents(SearchWeb.getConstants().reindexGpe());

        createReindexGpeToolStrip(reindexGpe.getToolStrip());

        reindexGpe.addViewCanvas(new InformationLabel(SearchWeb.getConstants().reindexGpeDescription()));

        CustomDynamicForm subpanel = createSubPanel();
        gpeStatus = new ViewTextItem(ReindexDS.GPE_STATUS, SearchWeb.getConstants().status());
        gpeLastExecutionSinceReboot = new ViewTextItem(ReindexDS.GPE_LAST_EXECUTION_SINCE_REBOOT, SearchWeb.getConstants().lastExecutionSinceServerReboot());
        subpanel.addFields(gpeStatus, gpeLastExecutionSinceReboot);

        reindexGpe.addViewCanvas(subpanel);
    }

    private void createReindexWeb() {
        reindexWeb = new ViewMainFormLayout();
        reindexWeb.setTitleLabelContents(SearchWeb.getConstants().reindexWeb());

        createReindexWebToolStrip(reindexWeb.getToolStrip());

        reindexWeb.addViewCanvas(new InformationLabel(SearchWeb.getConstants().reindexWebDescription()));

        CustomDynamicForm subpanel = createSubPanel();
        webStatus = new ViewTextItem(ReindexDS.WEB_STATUS, SearchWeb.getConstants().status());
        webLastExecutionSinceReboot = new ViewTextItem(ReindexDS.WEB_LAST_EXECUTION_SINCE_REBOOT, SearchWeb.getConstants().lastExecutionSinceServerReboot());
        subpanel.addFields(webStatus, webLastExecutionSinceReboot);

        reindexWeb.addViewCanvas(subpanel);
    }

    public void setCronGpeExpression(String cronGpeExpression) {
        gpeCronExpression.setValue(cronGpeExpression);
    }

    public void setCronWebExpression(String cronWebExpression) {
        webCronExpression.setValue(cronWebExpression);
    }

    private void createReindexGpeToolStrip(ToolStrip toolStrip) {
        reindexGpeStartButton = new ToolStripButton(SearchWeb.getConstants().start());
        toolStrip.addButton(reindexGpeStartButton);
    }

    private void createReindexWebToolStrip(ToolStrip toolStrip) {
        reindexWebStartButton = new ToolStripButton(SearchWeb.getConstants().start());
        toolStrip.addButton(reindexWebStartButton);
    }

    public ToolStripButton getReindexGpeStartButton() {
        return reindexGpeStartButton;
    }

    public ToolStripButton getReindexWebStartButton() {
        return reindexWebStartButton;
    }

    private CustomDynamicForm createSubPanel() {
        CustomDynamicForm subpanel = new CustomDynamicForm();
        subpanel.setColWidths("30%", "*");
        return subpanel;
    }

}

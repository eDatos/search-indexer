package es.gobcan.istac.search.web.client.widgets.view;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.toolstrip.view.MetamacToolStripViewImpl;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.events.HasClickHandlers;

import es.gobcan.istac.search.web.client.enums.SearchToolStripButtonEnum;
import es.gobcan.istac.search.web.client.widgets.SearchToolStripPresenterWidget.SearchToolStripView;

public class SearchToolStripViewImpl extends MetamacToolStripViewImpl implements SearchToolStripView {

    private CustomToolStripButton reindexButton;
    private CustomToolStripButton recommendedLinkListButton;
    private CustomToolStripButton recommendedKeywordListButton;

    @Inject
    public SearchToolStripViewImpl() {
        super();

        reindexButton = new CustomToolStripButton(getConstants().reindex());
        reindexButton.setID(SearchToolStripButtonEnum.REINDEX.getValue());

        recommendedLinkListButton = new CustomToolStripButton(getConstants().recommendedLinks());
        recommendedLinkListButton.setID(SearchToolStripButtonEnum.RECOMMENDED_LINKS.getValue());

        recommendedKeywordListButton = new CustomToolStripButton(getConstants().recommendedKeywords());
        recommendedKeywordListButton.setID(SearchToolStripButtonEnum.RECOMMENDED_KEYWORDS.getValue());

        toolStrip.addButton(reindexButton);
        toolStrip.addButton(recommendedLinkListButton);
        toolStrip.addButton(recommendedKeywordListButton);
    }

    @Override
    public HasClickHandlers getGoReindex() {
        return reindexButton;
    }

    @Override
    public HasClickHandlers getGoRecommendedLinkList() {
        return recommendedLinkListButton;
    }

    @Override
    public HasClickHandlers getGoRecommendedKeywordList() {
        return recommendedKeywordListButton;
    }
}

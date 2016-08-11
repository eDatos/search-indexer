/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.gobcan.istac.idxmanager.service.test.solr;

import static org.junit.Assert.assertEquals;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.gobcan.istac.idxmanager.service.test.util.EmbeddedSolrServerTestCaseBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class EmbeddedSolrTest extends EmbeddedSolrServerTestCaseBase {

    protected static Logger log = LoggerFactory.getLogger(EmbeddedSolrTest.class);

    @Test
    public void testDummy() throws Exception {
        UpdateRequest up = new UpdateRequest();
        up.setAction(ACTION.COMMIT, true, true);
        up.deleteByQuery("*:*");
        up.process(getIstacCore());
        up.clear();

        // Add
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", "AAA");
        up.add(doc);
        up.process(getIstacCore());

        SolrQuery q = new SolrQuery();
        QueryRequest r = new QueryRequest(q);
        q.setQuery("id:AAA");
        assertEquals(1, r.process(getIstacCore()).getResults().size());

    }

}

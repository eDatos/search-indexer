package es.gobcan.istac.search.core.idxmanager.service.stream;

public class KafkaConsumerContextInfo {

    private String  topicName;
    private String  clientId;
    private String  groupId;
    private boolean exitOnFinish    = false;
    private boolean finishedOnError = false;

    public KafkaConsumerContextInfo(String topicName, String clientId, String groupId) {
        super();
        this.topicName = topicName;
        this.clientId = clientId;
        this.groupId = groupId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setExitOnFinish(boolean exitOnFinish) {
        this.exitOnFinish = exitOnFinish;
    }

    public boolean isExitOnFinish() {
        return exitOnFinish;
    }

    public void setFinishedOnError(boolean finishOnError) {
        finishedOnError = finishOnError;
    }

    public boolean isFinishedOnError() {
        return finishedOnError;
    }
}

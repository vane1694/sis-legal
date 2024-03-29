package com.hildebrando.legal.modelo;
// Generated 13-jun-2012 10:20:15 by Hibernate Tools 3.4.0.CR1



/**
 * QrtzFiredTriggers generated by hbm2java
 */
public class QrtzFiredTriggers  implements java.io.Serializable {


     private String entryId;
     private String triggerName;
     private String triggerGroup;
     private String isVolatile;
     private String instanceName;
     private long firedTime;
     private long priority;
     private String state;
     private String jobName;
     private String jobGroup;
     private String isStateful;
     private String requestsRecovery;

    public QrtzFiredTriggers() {
    }

	
    public QrtzFiredTriggers(String entryId, String triggerName, String triggerGroup, String isVolatile, String instanceName, long firedTime, long priority, String state) {
        this.entryId = entryId;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.isVolatile = isVolatile;
        this.instanceName = instanceName;
        this.firedTime = firedTime;
        this.priority = priority;
        this.state = state;
    }
    public QrtzFiredTriggers(String entryId, String triggerName, String triggerGroup, String isVolatile, String instanceName, long firedTime, long priority, String state, String jobName, String jobGroup, String isStateful, String requestsRecovery) {
       this.entryId = entryId;
       this.triggerName = triggerName;
       this.triggerGroup = triggerGroup;
       this.isVolatile = isVolatile;
       this.instanceName = instanceName;
       this.firedTime = firedTime;
       this.priority = priority;
       this.state = state;
       this.jobName = jobName;
       this.jobGroup = jobGroup;
       this.isStateful = isStateful;
       this.requestsRecovery = requestsRecovery;
    }
   
    public String getEntryId() {
        return this.entryId;
    }
    
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
    public String getTriggerName() {
        return this.triggerName;
    }
    
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
    public String getTriggerGroup() {
        return this.triggerGroup;
    }
    
    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }
    public String getIsVolatile() {
        return this.isVolatile;
    }
    
    public void setIsVolatile(String isVolatile) {
        this.isVolatile = isVolatile;
    }
    public String getInstanceName() {
        return this.instanceName;
    }
    
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
    public long getFiredTime() {
        return this.firedTime;
    }
    
    public void setFiredTime(long firedTime) {
        this.firedTime = firedTime;
    }
    public long getPriority() {
        return this.priority;
    }
    
    public void setPriority(long priority) {
        this.priority = priority;
    }
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    public String getJobName() {
        return this.jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getJobGroup() {
        return this.jobGroup;
    }
    
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    public String getIsStateful() {
        return this.isStateful;
    }
    
    public void setIsStateful(String isStateful) {
        this.isStateful = isStateful;
    }
    public String getRequestsRecovery() {
        return this.requestsRecovery;
    }
    
    public void setRequestsRecovery(String requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
    }




}



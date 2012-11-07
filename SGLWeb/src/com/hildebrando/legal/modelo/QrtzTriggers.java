package com.hildebrando.legal.modelo;
// Generated 13-jun-2012 10:20:15 by Hibernate Tools 3.4.0.CR1


import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * QrtzTriggers generated by hbm2java
 */
public class QrtzTriggers  implements java.io.Serializable {


	 private static final long serialVersionUID = 1L;
	 private int item ;
	 
	 public int getItem() {
		return item;
	}
	 
	public void setItem(int item) {
		this.item = item;
	}

	 private QrtzTriggersId id;
     private QrtzJobDetails qrtzJobDetails;
     private String isVolatile;
     private String description;
     private Long nextFireTime;
     private String sNextFireTime;
     private Long prevFireTime;
     private String sPrevFireTime;
     private Long priority;
     private String triggerState;
     private String triggerType;
     private Date dStartTime;
     private long startTime;
     private String sStartTime;
     private Long endTime;
     private String sEndTime;
     private String calendarName;
     private Byte misfireInstr;
     private Blob jobData;
     private QrtzSimpleTriggers qrtzSimpleTriggers;
     private QrtzBlobTriggers qrtzBlobTriggers;
     private QrtzCronTriggers qrtzCronTriggers;
     private Set<QrtzTriggerListeners> qrtzTriggerListenerses = new HashSet<QrtzTriggerListeners>();

    public QrtzTriggers() {
    }

	
    public QrtzTriggers(QrtzTriggersId id, QrtzJobDetails qrtzJobDetails, String isVolatile, String triggerState, String triggerType, long startTime) {
        this.id = id;
        this.qrtzJobDetails = qrtzJobDetails;
        this.isVolatile = isVolatile;
        this.triggerState = triggerState;
        this.triggerType = triggerType;
        this.startTime = startTime;
    }
    public QrtzTriggers(QrtzTriggersId id, QrtzJobDetails qrtzJobDetails, String isVolatile, String description, Long nextFireTime, Long prevFireTime, Long priority, String triggerState, String triggerType, long startTime, Long endTime, String calendarName, Byte misfireInstr, Blob jobData, QrtzSimpleTriggers qrtzSimpleTriggers, QrtzBlobTriggers qrtzBlobTriggers, Set<QrtzTriggerListeners> qrtzTriggerListenerses, QrtzCronTriggers qrtzCronTriggers) {
       this.id = id;
       this.qrtzJobDetails = qrtzJobDetails;
       this.isVolatile = isVolatile;
       this.description = description;
       this.nextFireTime = nextFireTime;
       this.prevFireTime = prevFireTime;
       this.priority = priority;
       this.triggerState = triggerState;
       this.triggerType = triggerType;
       this.startTime = startTime;
       this.endTime = endTime;
       this.calendarName = calendarName;
       this.misfireInstr = misfireInstr;
       this.jobData = jobData;
       this.qrtzSimpleTriggers = qrtzSimpleTriggers;
       this.qrtzBlobTriggers = qrtzBlobTriggers;
       this.qrtzTriggerListenerses = qrtzTriggerListenerses;
       this.qrtzCronTriggers = qrtzCronTriggers;
    }
   
    public QrtzTriggersId getId() {
        return this.id;
    }
    
    public void setId(QrtzTriggersId id) {
        this.id = id;
    }
    public QrtzJobDetails getQrtzJobDetails() {
        return this.qrtzJobDetails;
    }
    
    public void setQrtzJobDetails(QrtzJobDetails qrtzJobDetails) {
        this.qrtzJobDetails = qrtzJobDetails;
    }
    public String getIsVolatile() {
        return this.isVolatile;
    }
    
    public void setIsVolatile(String isVolatile) {
        this.isVolatile = isVolatile;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getNextFireTime() {
        return this.nextFireTime;
    }
    
    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }
    public Long getPrevFireTime() {
        return this.prevFireTime;
    }
    
    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }
    public Long getPriority() {
        return this.priority;
    }
    
    public void setPriority(Long priority) {
        this.priority = priority;
    }
    public String getTriggerState() {
        return this.triggerState;
    }
    
    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }
    public String getTriggerType() {
        return this.triggerType;
    }
    
    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }
    public long getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public Long getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public String getCalendarName() {
        return this.calendarName;
    }
    
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }
    public Byte getMisfireInstr() {
        return this.misfireInstr;
    }
    
    public void setMisfireInstr(Byte misfireInstr) {
        this.misfireInstr = misfireInstr;
    }
    public Blob getJobData() {
        return this.jobData;
    }
    
    public void setJobData(Blob jobData) {
        this.jobData = jobData;
    }
    public QrtzSimpleTriggers getQrtzSimpleTriggers() {
        return this.qrtzSimpleTriggers;
    }
    
    public void setQrtzSimpleTriggers(QrtzSimpleTriggers qrtzSimpleTriggers) {
        this.qrtzSimpleTriggers = qrtzSimpleTriggers;
    }
    public QrtzBlobTriggers getQrtzBlobTriggers() {
        return this.qrtzBlobTriggers;
    }
    
    public void setQrtzBlobTriggers(QrtzBlobTriggers qrtzBlobTriggers) {
        this.qrtzBlobTriggers = qrtzBlobTriggers;
    }
    public Set<QrtzTriggerListeners> getQrtzTriggerListenerses() {
        return this.qrtzTriggerListenerses;
    }
    
    public void setQrtzTriggerListenerses(Set<QrtzTriggerListeners> qrtzTriggerListenerses) {
        this.qrtzTriggerListenerses = qrtzTriggerListenerses;
    }
    public QrtzCronTriggers getQrtzCronTriggers() {
        return this.qrtzCronTriggers;
    }
    
    public void setQrtzCronTriggers(QrtzCronTriggers qrtzCronTriggers) {
        this.qrtzCronTriggers = qrtzCronTriggers;
    }

	public String getsNextFireTime() {
		return sNextFireTime;
	}

	public void setsNextFireTime(String sNextFireTime) {
		this.sNextFireTime = sNextFireTime;
	}

	public String getsPrevFireTime() {
		return sPrevFireTime;
	}

	public void setsPrevFireTime(String sPrevFireTime) {
		this.sPrevFireTime = sPrevFireTime;
	}

	public String getsStartTime() {
		return sStartTime;
	}

	public void setsStartTime(String sStartTime) {
		this.sStartTime = sStartTime;
	}

	public String getsEndTime() {
		return sEndTime;
	}

	public void setsEndTime(String sEndTime) {
		this.sEndTime = sEndTime;
	}

	public Date getdStartTime() {
		return dStartTime;
	}

	public void setdStartTime(Date dStartTime) {
		this.dStartTime = dStartTime;
	}




}


package com.ku.bobo.generator.mysql.entity;

import java.io.Serializable;

/**
 * illness
 * @author 
 */
public class Illness implements Serializable {
    private Integer id;

    /**
     * 疾病名称
     */
    private String name;

    /**
     * 疾病等级
     */
    private String level;

    /**
     * 发病人数
     */
    private Integer peopleCount;

    /**
     * 治疗率
     */
    private Double treatmentRate;

    /**
     * 复发率
     */
    private Double recurRate;

    /**
     * 死亡率
     */
    private Double dieRate;

    /**
     * 病因
     */
    private String cause;

    /**
     * 症状
     */
    private String symptom;

    private String tallCrowd;

    private String lowCrowd;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Double getTreatmentRate() {
        return treatmentRate;
    }

    public void setTreatmentRate(Double treatmentRate) {
        this.treatmentRate = treatmentRate;
    }

    public Double getRecurRate() {
        return recurRate;
    }

    public void setRecurRate(Double recurRate) {
        this.recurRate = recurRate;
    }

    public Double getDieRate() {
        return dieRate;
    }

    public void setDieRate(Double dieRate) {
        this.dieRate = dieRate;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getTallCrowd() {
        return tallCrowd;
    }

    public void setTallCrowd(String tallCrowd) {
        this.tallCrowd = tallCrowd;
    }

    public String getLowCrowd() {
        return lowCrowd;
    }

    public void setLowCrowd(String lowCrowd) {
        this.lowCrowd = lowCrowd;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Illness other = (Illness) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getPeopleCount() == null ? other.getPeopleCount() == null : this.getPeopleCount().equals(other.getPeopleCount()))
            && (this.getTreatmentRate() == null ? other.getTreatmentRate() == null : this.getTreatmentRate().equals(other.getTreatmentRate()))
            && (this.getRecurRate() == null ? other.getRecurRate() == null : this.getRecurRate().equals(other.getRecurRate()))
            && (this.getDieRate() == null ? other.getDieRate() == null : this.getDieRate().equals(other.getDieRate()))
            && (this.getCause() == null ? other.getCause() == null : this.getCause().equals(other.getCause()))
            && (this.getSymptom() == null ? other.getSymptom() == null : this.getSymptom().equals(other.getSymptom()))
            && (this.getTallCrowd() == null ? other.getTallCrowd() == null : this.getTallCrowd().equals(other.getTallCrowd()))
            && (this.getLowCrowd() == null ? other.getLowCrowd() == null : this.getLowCrowd().equals(other.getLowCrowd()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getPeopleCount() == null) ? 0 : getPeopleCount().hashCode());
        result = prime * result + ((getTreatmentRate() == null) ? 0 : getTreatmentRate().hashCode());
        result = prime * result + ((getRecurRate() == null) ? 0 : getRecurRate().hashCode());
        result = prime * result + ((getDieRate() == null) ? 0 : getDieRate().hashCode());
        result = prime * result + ((getCause() == null) ? 0 : getCause().hashCode());
        result = prime * result + ((getSymptom() == null) ? 0 : getSymptom().hashCode());
        result = prime * result + ((getTallCrowd() == null) ? 0 : getTallCrowd().hashCode());
        result = prime * result + ((getLowCrowd() == null) ? 0 : getLowCrowd().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", level=").append(level);
        sb.append(", peopleCount=").append(peopleCount);
        sb.append(", treatmentRate=").append(treatmentRate);
        sb.append(", recurRate=").append(recurRate);
        sb.append(", dieRate=").append(dieRate);
        sb.append(", cause=").append(cause);
        sb.append(", symptom=").append(symptom);
        sb.append(", tallCrowd=").append(tallCrowd);
        sb.append(", lowCrowd=").append(lowCrowd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
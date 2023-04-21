package com.bside.threepick.domain.goal.entity;

import com.bside.threepick.common.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Goal extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_id", nullable = false, updatable = false)
  private Long accountId;

  @Column(name = "content", nullable = false, updatable = true)
  private String content;

  @Column(name = "time_value", nullable = false, updatable = true)
  private Long timeValue;

  @Column(name = "total_value", nullable = false, updatable = true)
  @Embedded
  private TotalValue totalValue;

  @Column(name = "status", nullable = true, updatable = true)
  @Enumerated(EnumType.STRING)
  private GoalStatus goalStatus;

  @Column(name = "hour", nullable = false, updatable = true)
  private int hour;

  @Column(name = "minute", nullable = false, updatable = true)
  private int minute;

  @Column(name = "weight", nullable = false, updatable = true)
  @Enumerated(EnumType.STRING)
  private Weight weight;

  @Column(name = "goal_date", nullable = false, updatable = false)
  private LocalDate goalDate;

  @Column(name = "goal_type", nullable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private GoalType goalType;

  protected Goal() {
  }

  public Goal(Long accountId, String content, Long timeValue, int hour, int minute, Weight weight, GoalType goalType) {
    this.accountId = accountId;
    this.content = content;
    this.timeValue = timeValue;
    this.totalValue = new TotalValue(hour, minute, timeValue, weight);
    this.goalStatus = GoalStatus.DOING;
    this.hour = hour;
    this.minute = minute;
    this.weight = weight;
    this.goalDate = LocalDate.now();
    this.goalType = goalType;
  }

  public void changeGoal(String content, GoalStatus goalStatus, int hour, int minute, Weight weight) {
    this.content = content;
    this.totalValue = new TotalValue(hour, minute, timeValue, weight);
    this.goalStatus = goalStatus;
    this.hour = hour;
    this.minute = minute;
    this.weight = weight;
  }

  public Long getId() {
    return id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public String getContent() {
    return content;
  }

  public TotalValue getTotalValue() {
    return totalValue;
  }

  public GoalStatus getGoalStatus() {
    return goalStatus;
  }

  public int getHour() {
    return hour;
  }

  public int getMinute() {
    return minute;
  }

  public Weight getWeight() {
    return weight;
  }
}

package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import pictures.taking.washing.persistence.JacksonConfig;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Schema(description="A landry machine (either washing machine or dryer).")
public class Machine implements Serializable {
  @Id
  @JsonProperty("id")
  private Long id = null;
  private Integer name = null;
  private Integer houseNumber = null;
  private Double cost = null;
  /**
   * The type of the machine
   */
  public enum TypeEnum {
    DRYER("Dryer"),

        WASHINGMACHINE("WashingMachine");
    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }
  private TypeEnum type = null;
  private Integer programDurationInMinutes = null;
  private Date lastStartTime = null;
  private Date lastHoldingStartTime = null;
  private UUID lastHoldingUserId = null;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE})
  @JsonBackReference
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  /**
   **/
  
  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, description = "")
  @JsonProperty("id")
  @NotNull
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * The name of the machine (number written on it&#x27;s front side)
   **/
  
  @Schema(example = "5", description = "The name of the machine (number written on it's front side)")
  @JsonProperty("name")
  public Integer getName() {
    return name;
  }
  public void setName(Integer name) {
    this.name = name;
  }

  /**
   * The house, in which the machine is located
   **/
  
  @Schema(example = "2", description = "The house, in which the machine is located")
  @JsonProperty("houseNumber")
  public Integer getHouseNumber() {
    return houseNumber;
  }
  public void setHouseNumber(Integer houseNumber) {
    this.houseNumber = houseNumber;
  }

  /**
   * The cost of one usage of the machine
   **/
  
  @Schema(example = "2.8", required = true, description = "The cost of one usage of the machine")
  @JsonProperty("cost")
  @NotNull
  public Double getCost() {
    return cost;
  }
  public void setCost(Double cost) {
    this.cost = cost;
  }

  /**
   * The type of the machine
   **/
  
  @Schema(example = "WashingMachine", required = true, description = "The type of the machine")
  @JsonProperty("type")
  @NotNull
  public TypeEnum getType() {
    return type;
  }
  public void setType(TypeEnum type) {
    this.type = type;
  }

  /**
   * The average duration of a program of the machine
   **/
  
  @Schema(example = "60", required = true, description = "The average duration of a program of the machine")
  @JsonProperty("programDurationInMinutes")
  @NotNull
  public Integer getProgramDurationInMinutes() {
    return programDurationInMinutes;
  }
  public void setProgramDurationInMinutes(Integer programDurationInMinutes) {
    this.programDurationInMinutes = programDurationInMinutes;
  }

  /**
   * The last time, thatthe machine was started
   **/
  
  @Schema(example = "2017-07-21T17:32:28Z", description = "The last time, thatthe machine was started")
  @JsonProperty("lastStartTime")
  public Date getLastStartTime() {
    return lastStartTime;
  }
  public void setLastStartTime(Date lastStartTime) {
    this.lastStartTime = lastStartTime;
  }

  /**
   * The last time, that someone held this machine
   **/
  
  @Schema(example = "2017-07-21T17:32:28Z", description = "The last time, that someone held this machine")
  @JsonProperty("lastHoldingStartTime")
  public Date getLastHoldingStartTime() {
    return lastHoldingStartTime;
  }
  public void setLastHoldingStartTime(Date lastHoldingStartTime) {
    this.lastHoldingStartTime = lastHoldingStartTime;
  }

  /**
   * The last user, that held this machine
   **/
  
  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", description = "The last user, that held this machine")
  @JsonProperty("lastHoldingUserId")
  public UUID getLastHoldingUserId() {
    return lastHoldingUserId;
  }
  public void setLastHoldingUserId(UUID lastHoldingUserId) {
    this.lastHoldingUserId = lastHoldingUserId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Machine machine = (Machine) o;
    return Objects.equals(id, machine.id) &&
        Objects.equals(name, machine.name) &&
        Objects.equals(houseNumber, machine.houseNumber) &&
        Objects.equals(cost, machine.cost) &&
        Objects.equals(type, machine.type) &&
        Objects.equals(programDurationInMinutes, machine.programDurationInMinutes) &&
        Objects.equals(lastStartTime, machine.lastStartTime) &&
        Objects.equals(lastHoldingStartTime, machine.lastHoldingStartTime) &&
        Objects.equals(lastHoldingUserId, machine.lastHoldingUserId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, houseNumber, cost, type, programDurationInMinutes, lastStartTime, lastHoldingStartTime, lastHoldingUserId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Machine {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    programDurationInMinutes: ").append(toIndentedString(programDurationInMinutes)).append("\n");
    sb.append("    lastStartTime: ").append(toIndentedString(lastStartTime)).append("\n");
    sb.append("    lastHoldingStartTime: ").append(toIndentedString(lastHoldingStartTime)).append("\n");
    sb.append("    lastHoldingUserId: ").append(toIndentedString(lastHoldingUserId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

package frc.robot.subsystems.feeder;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import frc.robot.generic.rollers.GenericVoltageRollerSystem;
import java.util.function.DoubleSupplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class FeederSubsystem extends GenericVoltageRollerSystem<FeederSubsystem.FeederGoal> {
  @RequiredArgsConstructor
  @Getter
  public enum FeederGoal implements VoltageGoal {
    IDLING(() -> 0.0), // Intake is off
    FORWARD(() -> 12.0), // Maximum forward voltage
    REVERSE(() -> -12.0); // Maximum reverse voltage

    private final DoubleSupplier voltageSupplier;
  }

  @Setter private FeederGoal goal = FeederGoal.IDLING;
  private Debouncer currentDebouncer = new Debouncer(0.25, DebounceType.kFalling);

  public FeederSubsystem(String name, FeederIO io) {
    super(name, io);
  }

  public boolean hasNote() {
    return goal == FeederGoal.FORWARD
        && stateTimer.hasElapsed(0.25)
        && currentDebouncer.calculate(inputs.torqueCurrentAmps > 45.0);
  }
}

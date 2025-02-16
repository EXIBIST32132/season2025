package frc.robot.generic.rollers;

import static frc.robot.Config.Subsystems.ALGAE_INTAKE_ENABLED;
import static frc.robot.Config.Subsystems.CORAL_INTAKE_ENABLED;
import static frc.robot.GlobalConstants.MODE;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.GlobalConstants;
import frc.robot.subsystems.algae.*;
import frc.robot.subsystems.coral.*;
import lombok.Getter;

public class Rollers extends SubsystemBase {
  @Getter
  private final AlgaeIntakeSubsystem algaeIntake =
      ALGAE_INTAKE_ENABLED
          ? (MODE == GlobalConstants.RobotMode.REAL
              ? AlgaeIntakeConstants.isFlex
                  ? new AlgaeIntakeSubsystem("AlgaeIntake", new AlgaeIntakeIOFlex())
                  : new AlgaeIntakeSubsystem("AlgaeIntake", new AlgaeIntakeIOMax())
              : new AlgaeIntakeSubsystem(
                  "Algae Sim Intake", new AlgaeIntakeIOSim(new DCMotor(1, 1, 1, 1, 1, 1), 0, 0)))
          : null;

  @Getter
  private final CoralIntakeSubsystem coralIntake =
      CORAL_INTAKE_ENABLED
          ? (MODE == GlobalConstants.RobotMode.REAL
              ? CoralIntakeConstants.IS_FLEX
                  ? new CoralIntakeSubsystem("CoralIntake", new CoralIntakeIOFlex())
                  : new CoralIntakeSubsystem("CoralIntake", new CoralIntakeIOMax())
              : new CoralIntakeSubsystem(
                  "Coral Sim Intake", new CoralIntakeIOSim(new DCMotor(1, 1, 1, 1, 1, 1), 0, 0)))
          : null;

  @Override
  public void periodic() {
    if (ALGAE_INTAKE_ENABLED) algaeIntake.periodic();
    if (CORAL_INTAKE_ENABLED) coralIntake.periodic();
  }
}

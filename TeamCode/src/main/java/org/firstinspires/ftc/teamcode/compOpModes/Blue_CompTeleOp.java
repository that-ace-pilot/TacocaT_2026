package org.firstinspires.ftc.teamcode.compOpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlowerIntake;
import org.firstinspires.ftc.teamcode.mechanisms.FlowerPlower;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.PositionalAim;
import org.firstinspires.ftc.teamcode.mechanisms.Turret;
@TeleOp
public class Blue_CompTeleOp extends OpMode {
    boolean turretManual = true;
    double driveTrainForward = 0 ;
    double driveTrainStrafe = 0 ;
    double driveTrainRotate = 0 ;
    MecanumDrive drive = new MecanumDrive();
    Intake intake = new Intake();
    FlowerPlower plow = new FlowerPlower();
    FlowerIntake flowerIntake = new FlowerIntake();
    Turret turret = new Turret();
    PositionalAim autoAim = new PositionalAim();
    org.firstinspires.ftc.teamcode.mechanisms.ShooterPIDFTesting shooter = new org.firstinspires.ftc.teamcode.mechanisms.ShooterPIDFTesting();
    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        plow.init(hardwareMap);
        //ShooterPIDFTesting.init(this, true);
        turret.init(hardwareMap);
        autoAim.init(hardwareMap, this, true, false, null, turret);
        autoAim.aimEnabled = false;
        autoAim.launchEnabled = false;
    }

    @Override
    public void loop() {
        autoAim.loop();
        //turret.loop();
        if (gamepad1.left_stick_button) {
            driveTrainForward = -gamepad1.left_stick_y * .5;
            driveTrainStrafe = gamepad1.left_stick_x * .5;
            driveTrainRotate = gamepad1.right_stick_x * .5;
        } else {
            driveTrainForward = -gamepad1.left_stick_y;
            driveTrainStrafe = gamepad1.left_stick_x;
            driveTrainRotate = gamepad1.right_stick_x;
        }
        drive.driveFieldRelative(driveTrainForward, driveTrainStrafe, driveTrainRotate);
            if (gamepad1.right_trigger != 0) {
                intake.startBothIntake();
            } else if (gamepad1.left_trigger != 0) {
                intake.reverseBothIntake();
            } else {
                intake.stopIntake();
            }
            if (gamepad1.xWasPressed()) {
                plow.plowUp();
            } else if (gamepad1.aWasPressed()) {
                plow.plowDown();
            } else if (gamepad1.bWasPressed()) {
                plow.intake();
            }
                if (gamepad2.right_trigger + gamepad2.left_trigger > 0) {
                    if (gamepad2.right_trigger != 0) {
                        autoAim.launchEnabled = true;
                    }
                    if (gamepad2.left_trigger != 0) {
                        turret.setLauncherVelocity(-500);
                    }
                } else {
                    autoAim.launchEnabled = false;
                    turret.stopShooter();
                }
                if (gamepad2.aWasReleased()) {
                    intake.gateClosed();
                    intake.stopIntake();
                }
                if (gamepad2.a) {
                    intake.gateOpen();
                    intake.startBothIntake();
                }
                if (gamepad2.yWasPressed()) {
                    intake.gateOpen();
                }
                if (gamepad2.bWasPressed()) {
                    intake.gateClosed();
                }
            /*
            if (turretManual = true) {
                autoAim.aimEnabled = false;
                turret.setTurretRotate(-gamepad2.left_stick_x);
            } else {
                autoAim.aimEnabled = true;
            }
            */
            telemetry.addData("Distance From Goal:", autoAim.rangeToGoal);
            telemetry.addData("Shooter Set Velocity:", turret.velocity);
            telemetry.addData("Shooter Current Velocity:", turret.shooter1.getVelocity());
            telemetry.addData("Turret Angel:", turret.totalTurretDegrees);
        }
}

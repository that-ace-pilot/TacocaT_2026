package org.firstinspires.ftc.teamcode.compOpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlowerIntake;
import org.firstinspires.ftc.teamcode.mechanisms.FlowerPlower;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.PedroFieldRel;
import org.firstinspires.ftc.teamcode.mechanisms.PositionalAim;
import org.firstinspires.ftc.teamcode.mechanisms.Turret;
@TeleOp
public class Blue_CompTeleOp extends OpMode {
    boolean driveManual = false;
    boolean turretManual = false;
    int damping = 6;
    double driveTrainForward = 0 ;
    double driveTrainStrafe = 0 ;
    double driveTrainRotate = 0 ;
    MecanumDrive drive = new MecanumDrive();
    Intake intake = new Intake();
    FlowerPlower plow = new FlowerPlower();
    FlowerIntake flowerIntake = new FlowerIntake();
    Turret turret = new Turret();
    PositionalAim autoAim = new PositionalAim();
    PedroFieldRel fieldRel = new PedroFieldRel();
    org.firstinspires.ftc.teamcode.mechanisms.ShooterPIDFTesting shooter = new org.firstinspires.ftc.teamcode.mechanisms.ShooterPIDFTesting();
    @Override
    public void init() {
        fieldRel.init(hardwareMap, false);
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
        if (driveManual) {
            autoAim.loop();
            turret.loop();
            if (gamepad1.right_trigger != 0) {
                intake.startBothIntake();
            } else if (gamepad1.left_trigger != 0) {
                intake.reverseBothIntake();
            } else {
                intake.stopIntake();
            }
            if (gamepad1.xWasPressed() || gamepad1.squareWasPressed()) {
                plow.plowUp();
            } else if (gamepad1.aWasPressed() || gamepad1.crossWasPressed()) {
                plow.plowDown();
            } else if (gamepad1.bWasPressed() || gamepad1.circleWasPressed()) {
                plow.intake();
            }
            if (gamepad1.dpad_up || gamepad2.dpad_down) {
                driveManual = !driveManual;
            }
            if (gamepad1.left_stick_x == 0) {
                damping = 6;
            }
            if (gamepad1.left_stick_button) {
                driveTrainForward = -gamepad1.left_stick_y * .5;
                driveTrainStrafe = gamepad1.left_stick_x * .5;
                driveTrainRotate = gamepad1.right_stick_x * .5;
            } else {
                driveTrainForward = -gamepad1.left_stick_y;
                driveTrainStrafe = gamepad1.left_stick_x / damping;
                driveTrainRotate = gamepad1.right_stick_x;
                if (damping > 1) {
                    damping -= 1;
                }
            }
            drive.drive(driveTrainForward, driveTrainStrafe, driveTrainRotate);
            if (gamepad2.right_trigger + gamepad2.left_trigger > 0) {
                if (gamepad2.right_trigger != 0) {
                    turret.setLauncherVelocity(1420);
                }
                if (gamepad2.left_trigger != 0) {
                    turret.setLauncherVelocity(-500);
                }
            } else {
                turret.stopShooter();
            }
            if (gamepad2.aWasReleased() || gamepad2.crossWasReleased()) {
                intake.gateClosed();
                intake.stopIntake();
            }
            if (gamepad2.a || gamepad2.cross) {
                intake.gateOpen();
                intake.startBothIntake();
            }
            if (gamepad2.yWasPressed() || gamepad2.triangleWasPressed()) {
                intake.gateOpen();
            }
            if (gamepad2.bWasPressed() || gamepad2.circleWasPressed()) {
                intake.gateClosed();
            }
            if (gamepad2.dpad_up) {
                autoAim.reset();
            }
            if (gamepad2.leftStickButtonWasPressed()) {
                turretManual = !turretManual;
            }
            if (turretManual) {
                autoAim.aimEnabled = false;
                turret.setTurretRotate(-gamepad2.left_stick_x);
            } else {
                autoAim.aimEnabled = true;
            }
            telemetry.addData("Distance From Goal:", autoAim.rangeToGoal);
            telemetry.addData("Shooter Set Velocity:", turret.velocity);
            telemetry.addData("Shooter Current Velocity:", turret.shooter1.getVelocity());
            telemetry.addData("Turret Angel:", turret.totalTurretDegrees);

        } else {

            autoAim.loop();
            turret.loop();
            if (gamepad1.right_trigger != 0) {
                intake.startBothIntake();
            } else if (gamepad1.left_trigger != 0) {
                intake.reverseBothIntake();
            } else {
                intake.stopIntake();
            }
            if (gamepad1.xWasPressed() || gamepad1.squareWasPressed()) {
                plow.plowUp();
            } else if (gamepad1.aWasPressed() || gamepad1.crossWasReleased()) {
                plow.plowDown();
            } else if (gamepad1.bWasPressed() || gamepad1.circleWasPressed()) {
                plow.intake();
            }
            if (gamepad1.dpad_up) {
                driveManual = !driveManual;
            }
            if (gamepad1.left_stick_x == 0) {
                damping = 4;
            }
            if (gamepad1.left_stick_button) {
                driveTrainForward = gamepad1.left_stick_y * .5;
                driveTrainStrafe = gamepad1.left_stick_x * .5;
                driveTrainRotate = gamepad1.right_stick_x * .5;
            } else {
                driveTrainForward = gamepad1.left_stick_y;
                driveTrainStrafe = gamepad1.left_stick_x / damping;
                driveTrainRotate = gamepad1.right_stick_x;
                if (damping > 1) {
                    damping -= 1;
                }
            }
            fieldRel.loop(driveTrainForward, driveTrainStrafe, driveTrainRotate);
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
            if (gamepad2.aWasReleased() || gamepad2.crossWasReleased()) {
                intake.gateClosed();
                intake.stopIntake();
            }
            if (gamepad2.a || gamepad2.cross) {
                intake.gateOpen();
                intake.startBothIntake();
            }
            if (gamepad2.yWasPressed() || gamepad2.triangleWasPressed()) {
                intake.gateOpen();
            }
            if (gamepad2.bWasPressed() || gamepad2.circleWasPressed()) {
                intake.gateClosed();
            }
            if (gamepad2.dpad_up) {
                autoAim.reset();
            }
            if (gamepad2.leftStickButtonWasPressed()) {
                turretManual = !turretManual;
            }
            if (turretManual) {
                autoAim.aimEnabled = false;
                turret.setTurretRotate(-gamepad2.left_stick_x);
            } else {
                autoAim.aimEnabled = true;
            }
            telemetry.addData("Distance From Goal:", autoAim.rangeToGoal);
            telemetry.addData("Shooter Set Velocity:", turret.velocity);
            //telemetry.addData("Shooter Current Velocity:", turret.shooter1.getVelocity());
            telemetry.addData("Turret Angel:", autoAim.bearingToGoal);
        }
    }
}
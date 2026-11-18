package org.firstinspires.ftc.teamcode.testOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Turret;

@TeleOp
public class ShooterPIDFTuner extends OpMode {
    Turret turret = new Turret();
    @Override
    public void init() {
        turret.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            turret.setShooterPower(1);
        } else {
            turret.stopShooter();
        }
        turret.PIDFCalibration();
        telemetry.addData("P:", turret.predictedP);
        telemetry.addData("I:", turret.predictedI);
        telemetry.addData("D:", turret.predictedD);
        telemetry.addData("F:", turret.predictedF);
        telemetry.update();
    }
}

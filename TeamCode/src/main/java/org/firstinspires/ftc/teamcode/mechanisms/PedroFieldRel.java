package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AutoRoutines.Commands;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class PedroFieldRel {
    private Follower follower;
    private PoseFactory poseFactory = PoseFactory.degrees();
    private Pose start;
    private boolean red;
    private final Pose redStart = poseFactory.of(14.5, 122, 0);
    private final Pose blueStart = poseFactory.of(130, 20, 180);
    public void init(HardwareMap hardwareMap, boolean Red) {
        follower = Constants.create(hardwareMap);

        if (Red) {
            start = redStart;
            red = true;
        } else {
            start = blueStart;
            red = false;
        }

        if (Commands.lastPosition != null) {
            follower.setPose(Commands.lastPosition);
        } else {
            follower.setPose(start);
        }
        follower.update();
    }
    public void loop(double forward, double strafe, double rotate) {
        if (red) {
            forward = -forward;
            strafe = -strafe;
        }

        DrivePowers powers = ManualDrive.fieldCentric(
                forward,
                strafe,
                -rotate,
                follower.pose().heading()
        );
        ManualDrive.driveOrHold(follower,powers);
        follower.update();
    }
}
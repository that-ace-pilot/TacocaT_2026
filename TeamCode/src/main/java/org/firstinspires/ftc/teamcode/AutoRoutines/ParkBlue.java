package org.firstinspires.ftc.teamcode.AutoRoutines;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

public class ParkBlue extends OpMode {
    public static Pose lastPosition = null;
    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(133.5, 63, 180);
    private final Pose park = poseFactory.of(130, 49.5, 180);
    public Path park() {return line(start, park).constant(180);}
    private Command autoRoutine() {return follow(follower, park());}

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop(){
        follower.update();
        Scheduler.execute();

        lastPosition = follower.pose();
        Commands.setLastPosition(lastPosition);
    }
}

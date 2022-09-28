package org.firstinspires.ftc.teamcode.autonomy

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.hardware.Hardware

@Autonomous
@Config
class Auto24H : AutoBase() {
    private val startPose = Pose2d(0.0, 0.0, Math.toRadians(180.0))
    private val deployPose = Pose2d(38.0,0.0,Math.toRadians(150.0))
    private val intermediatePose = Pose2d(30.0,20.0,Math.toRadians(180.0))
    private val parkingPose = Pose2d(5.0,20.0,Math.toRadians(180.0))
    var a = Pose2d(0.0,0.0,0.0)

    override fun preInit() {
        super.preInit()
        telemetry.addLine("Initializing...")
        telemetry.update()
        drive.poseEstimate = startPose
    }

    override fun Hardware.run() {
        drive.followTrajectory(
                drive.trajectoryBuilder(startPose,true)
                        .addTemporalMarker(0.2){
                            outtake.grabElement()
                            outtake.moveUp()
                        }
                        .lineToLinearHeading(deployPose)
                        .build()
        )

        sleep(1000)
        outtake.releaseElement()
        sleep(500)
        outtake.moveDown()
        sleep(1000)
        outtake.servoMove.position = 0.0

        drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                        .lineToLinearHeading(startPose)
                        .build()
        )

        drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                        .lineTo(Vector2d(parkingPose.x,parkingPose.y))
                        .build()
        )
    }
}
package org.firstinspires.ftc.teamcode.autonomy

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.hardware.Hardware
import org.firstinspires.ftc.teamcode.waitMillis
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.hardware.Outtake

@Autonomous
@Config
class SanityTest : AutoBase() {

    var offset = 6.0


    override fun preInit() {
        super.preInit()
        telemetry.addLine("Initializing...")
        telemetry.update()
    }

    override fun Hardware.run() {
        //Stop streaming
        webcam.stopStreaming()
    }

}
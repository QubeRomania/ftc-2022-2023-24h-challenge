package org.firstinspires.ftc.teamcode.autonomy

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.hardware.Outtake
import org.firstinspires.ftc.teamcode.waitMillis
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation

@Config
abstract class AutoBase : org.firstinspires.ftc.teamcode.OpMode() {
    val drive : SampleMecanumDrive by lazy {
        SampleMecanumDrive(hardwareMap)
    }

    val intakePower = -1.0

    override fun preInit() {
        //telemetry.addLine("Initializing")
        //telemetry.update()

        //Start Streaming
        /*webcam.openCameraDevice()
        webcam.setPipeline(pipeline)
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT)
         */
        hw.outtake.servoMove.position = 0.0
        hw.outtake.grabElement()
    }

    override fun preInitLoop() {
        /*position = pipeline.analysis
        telemetry.addData("Team Element position", position)
        telemetry.update()
        telemetry.addLine("Waiting for start...")
        telemetry.update()
         */
    }

    /**
     * Camera related code
     * -------------------------------------------------------------
     * */

    val webcam: OpenCvCamera by lazy {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class.java, "Webcam 1"), cameraMonitorViewId)
    }

    var pipeline = TeamElementPipeline(telemetry)
    var position = pipeline.analysis
}
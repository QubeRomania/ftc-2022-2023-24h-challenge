package org.firstinspires.ftc.teamcode.Tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.Gamepad
import org.firstinspires.ftc.teamcode.hardware.Hardware
import org.firstinspires.ftc.teamcode.hardware.Outtake
import java.lang.Exception
import java.lang.Math.atan2
import kotlin.math.absoluteValue

@TeleOp(name = "MotorTest", group = "Main")
class MotorTest: LinearOpMode() {

    override fun runOpMode() {
        val gp1 = Gamepad(gamepad1)
        val gp2 = Gamepad(gamepad2)

        val outtakeSlider1 = hardwareMap.dcMotor["outtakeMotor"] ?: throw Exception("Failed to find motor outtakeSlider1")

        outtakeSlider1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        outtakeSlider1.direction = DcMotorSimple.Direction.FORWARD
        outtakeSlider1.mode = DcMotor.RunMode.RUN_USING_ENCODER
        outtakeSlider1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        waitForStart()

        while(opModeIsActive())
        {
            outtakeSlider1.targetPosition = 800
            outtakeSlider1.mode = DcMotor.RunMode.RUN_TO_POSITION
            outtakeSlider1.power = Outtake.OUTTAKE_POWER
            telemetry.addData("position",outtakeSlider1.currentPosition)
            telemetry.update()
        }
    }
}
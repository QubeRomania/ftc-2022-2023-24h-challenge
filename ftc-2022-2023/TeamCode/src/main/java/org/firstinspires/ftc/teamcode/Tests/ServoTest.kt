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

@TeleOp(name = "ServoTest", group = "Main")
class ServoTest: LinearOpMode() {

    val posOpen = 1.0
    val posClose = 0.0

    override fun runOpMode() {
        val gp1 = Gamepad(gamepad1)

        val servoTester = hardwareMap.servo["servoTester"] ?: throw Exception("Failed to find servo servoTester")
        servoTester.position = posClose

        waitForStart()

        while(opModeIsActive())
        {
            if(gp1.checkToggle(Gamepad.Button.DPAD_UP))
                servoTester.position = servoTester.position+0.01
            if(gp1.checkToggle(Gamepad.Button.DPAD_DOWN))
                servoTester.position = servoTester.position-0.01

            if(gp1.checkToggle(Gamepad.Button.X))
                servoTester.position = posOpen
            if(gp1.checkToggle(Gamepad.Button.Y))
                servoTester.position = posClose
            telemetry.addData("position",servoTester.position)
            telemetry.update()
        }
    }
}
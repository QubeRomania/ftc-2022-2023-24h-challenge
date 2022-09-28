package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Hardware
import java.lang.Math.atan2

@TeleOp(name = "CompleteDrive", group = "Main")
class CompleteDrive: OpMode() {

    override fun preInit() {
    }

    override fun preInitLoop() {
        telemetry.addLine("Waiting for start...")
        telemetry.update()
        idle()
    }

    override fun Hardware.run() {
        val gp1 = Gamepad(gamepad1)
        val gp2 = Gamepad(gamepad2)

        var driveScale = 0.8
        var slowScale = 0.2

        var isClosedClaw = false

        waitForStart()
        while(opModeIsActive())
        {
            val power = speed
            val rotPower = rotation
            if(gp1.checkHold(Gamepad.Button.RIGHT_BUMPER))
                hw.motors.move(direction, power*slowScale, rotPower*slowScale)
            else
                hw.motors.move(direction, power*driveScale, rotPower*driveScale)

            if(gp2.checkToggle(Gamepad.Button.RIGHT_BUMPER))
                outtake.moveUp()

            if(gp2.checkToggle(Gamepad.Button.LEFT_BUMPER))
                outtake.moveDown()

            if(gp2.checkToggle(Gamepad.Button.A))
            {
                if(!isClosedClaw)
                {
                    isClosedClaw = true
                    outtake.grabElement()
                }
                else
                {
                    isClosedClaw = false
                    outtake.releaseElement()
                }
            }

            telemetry.addData("OuttakeMotor current pos", outtake.outtakeMotor.currentPosition)
            telemetry.addData("OuttakeMotor target pos", outtake.outtakeMotor.targetPosition)
            telemetry.update()
        }
    }

    ///The direction in which the robot is translating
    private val direction: Double
        get() {
            val x = gamepad1.left_stick_x.toDouble()
            val y = -gamepad1.left_stick_y.toDouble()

            return atan2(y, x) / Math.PI * 180.0 - 90.0
        }

    /// Rotation around the robot's Z axis.
    private val rotation: Double
        get() = -gamepad1.right_stick_x.toDouble()

    /// Translation speed.
    private val speed: Double
        get() {
            val x = gamepad1.left_stick_x.toDouble()
            val y = gamepad1.left_stick_y.toDouble()

            return Math.sqrt((x * x) + (y * y))
        }
}
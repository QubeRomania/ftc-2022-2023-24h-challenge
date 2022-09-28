package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.rev.Rev2mDistanceSensor
import com.qualcomm.hardware.rev.RevTouchSensor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Position
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.lang.Exception
import java.util.*
import kotlin.math.absoluteValue


/**
 * OutTake subsystem.
 *
 * This class controls the hardware for placing freight
 */
class Outtake(hwMap: HardwareMap) {
    companion object {
        const val servoClawOpen = 0.10
        const val servoClawClose = 0.92

        const val servoMoveOpen = 0.83 //0.47
        const val servoMoveClose = 0.52 //0.16

        const val servoRotateOpen = 0.95 //0.36
        const val servoRotateClose = 0.26 //0.95

        const val OUTTAKE_POWER = 0.7

        const val ARM_CLOSE = 0
        const val ARM_OPEN = 2220
    }

    val outtakeMotor = hwMap.dcMotor["outtakeMotor"] ?: throw Exception("Failed to find outtakeMotor")

    val servoClaw = hwMap.servo["servoClaw"] ?: throw Exception("Failed to find servo servoClaw")
    val servoMove = hwMap.servo["servoMove"] ?: throw Exception("Failed to find servo servoMove")
    val servoRotate = hwMap.servo["servoRotate"] ?: throw Exception("Failed to find servoRotate")

    var outtakePosition: Int = 0

    init {
        releaseElement()
        servoMove.position = servoMoveClose
        servoRotate.position = servoRotateClose

        outtakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        outtakeMotor.direction = DcMotorSimple.Direction.FORWARD
        outtakeMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        outtakeMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER


    }

    fun moveUp()
    {
        outtakePosition = ARM_OPEN
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = OUTTAKE_POWER

        servoMove.position = servoMoveOpen
        servoRotate.position = servoRotateOpen
    }

    fun moveDown()
    {
        outtakePosition = ARM_CLOSE
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = OUTTAKE_POWER

        servoMove.position = servoMoveClose
        servoRotate.position = servoRotateClose

        releaseElement()
    }

    fun grabElement()
    {
        setClawPositions(servoClawOpen)
    }

    fun releaseElement()
    {
        setClawPositions(servoClawClose)
    }

    fun setClawPositions(position: Double)
    {
        servoClaw.position = position
    }

    fun stop()
    {
        outtakeMotor.power = 0.0
    }

}
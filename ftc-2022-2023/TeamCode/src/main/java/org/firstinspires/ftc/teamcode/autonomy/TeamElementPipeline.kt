package org.firstinspires.ftc.teamcode.autonomy

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvPipeline

class TeamElementPipeline(private val telemetry: Telemetry) : OpenCvPipeline() {
    var result = 0.0

    enum class FreightFrenzyPosition {
        LEFT, CENTER, RIGHT
    }

    @Volatile
    var analysis = FreightFrenzyPosition.LEFT
        private set

    // Working Mat variables
    var blur = Mat()
    var hsv = Mat()
    var channel = Mat()
    var thold = Mat()
    var region1_Cb: Mat? = null
    var region2_Cb: Mat? = null
    var region3_Cb: Mat? = null
    var avg1 = 0
    var avg2 = 0
    var avg3 = 0

    // Drawing variables
    val BLUE = Scalar(0.0, 0.0, 255.0)
    val GREEN = Scalar(0.0, 255.0, 0.0)
    var region1_pointA = Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y)
    var region1_pointB = Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT)
    var region2_pointA = Point(
            REGION2_TOPLEFT_ANCHOR_POINT.x,
            REGION2_TOPLEFT_ANCHOR_POINT.y)
    var region2_pointB = Point(
            REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT)
    var region3_pointA = Point(
            REGION3_TOPLEFT_ANCHOR_POINT.x,
            REGION3_TOPLEFT_ANCHOR_POINT.y)
    var region3_pointB = Point(
            REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT)

    override fun processFrame(input: Mat): Mat {

        // Img processing
        Imgproc.medianBlur(input, blur, 5)
        Imgproc.cvtColor(blur, hsv, Imgproc.COLOR_RGB2HSV)
        Core.extractChannel(hsv, channel, 1)
        Imgproc.threshold(channel, thold, 120.0, 255.0, Imgproc.THRESH_BINARY)
        region1_Cb = thold.submat(Rect(region1_pointA, region1_pointB))
        region2_Cb = thold.submat(Rect(region2_pointA, region2_pointB))
        region3_Cb = thold.submat(Rect(region3_pointA, region3_pointB))
        avg1 = Core.mean(region1_Cb).`val`[0].toInt()
        avg2 = Core.mean(region2_Cb).`val`[0].toInt()
        avg3 = Core.mean(region3_Cb).`val`[0].toInt()
        Imgproc.rectangle(
                input,  // Buffer to draw on
                region1_pointA,  // First point which defines the rectangle
                region1_pointB,  // Second point which defines the rectangle
                BLUE,  // The color the rectangle is drawn in
                2) // Thickness of the rectangle lines

        /*
         * Draw a rectangle showing sample region 2 on the screen.
         * Simply a visual aid. Serves no functional purpose.
         */Imgproc.rectangle(
                input,  // Buffer to draw on
                region2_pointA,  // First point which defines the rectangle
                region2_pointB,  // Second point which defines the rectangle
                BLUE,  // The color the rectangle is drawn in
                2) // Thickness of the rectangle lines

        /*
         * Draw a rectangle showing sample region 3 on the screen.
         * Simply a visual aid. Serves no functional purpose.
         */Imgproc.rectangle(
                input,  // Buffer to draw on
                region3_pointA,  // First point which defines the rectangle
                region3_pointB,  // Second point which defines the rectangle
                BLUE,  // The color the rectangle is drawn in
                2) // Thickness of the rectangle lines


        /*
         * Find the max of the 3 averages
         */
        val maxOneTwo = Math.max(avg1, avg2)
        val max = Math.max(maxOneTwo, avg3)

        /*
         * Now that we found the max, we actually need to go and
         * figure out which sample region that value was from
         */if (max == avg1) // Was it from region 1?
        {
            result = 0.0
            analysis = FreightFrenzyPosition.LEFT

            /*
             * Draw a solid rectangle on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */Imgproc.rectangle(
                input,  // Buffer to draw on
                region1_pointA,  // First point which defines the rectangle
                region1_pointB,  // Second point which defines the rectangle
                GREEN,  // The color the rectangle is drawn in
                -1) // Negative thickness means solid fill
        } else if (max == avg2) // Was it from region 2?
        {
            result = 1.0
            analysis = FreightFrenzyPosition.CENTER

            /*
             * Draw a solid rectangle on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */Imgproc.rectangle(
                input,  // Buffer to draw on
                region2_pointA,  // First point which defines the rectangle
                region2_pointB,  // Second point which defines the rectangle
                GREEN,  // The color the rectangle is drawn in
                -1) // Negative thickness means solid fill
        } else if (max == avg3) // Was it from region 3?
        {
            result = 2.0
            analysis = FreightFrenzyPosition.RIGHT

            /*
             * Draw a solid rectangle on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */Imgproc.rectangle(
                input,  // Buffer to draw on
                region3_pointA,  // First point which defines the rectangle
                region3_pointB,  // Second point which defines the rectangle
                GREEN,  // The color the rectangle is drawn in
                -1) // Negative thickness means solid fill
        }

        /*
         * Render the 'input' buffer to the viewport. But note this is not
         * simply rendering the raw camera feed, because we called functions
         * to add some annotations to this buffer earlier up.
         */return input
    }

    companion object {
        val REGION1_TOPLEFT_ANCHOR_POINT = Point(20.0, 150.0)
        val REGION2_TOPLEFT_ANCHOR_POINT = Point(140.0, 150.0)
        val REGION3_TOPLEFT_ANCHOR_POINT = Point(260.0, 150.0)
        const val REGION_WIDTH = 40
        const val REGION_HEIGHT = 40
    }
}
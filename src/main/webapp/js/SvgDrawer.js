let WIDTH = 400;
let HEIGHT = 400;
const X_CENTER = 0;
const Y_CENTER = 0;
const BACKGROUND_COLOR = '#fff';
let CANVAS = null;
let globalPointsList = {};
const AXES_COLOR = '#000'
const AREA_COLOR = '#AF412D';
let scale = 0.035;
const pointsScale = 5;

let lastElementNum = 0;
const DEFAULT_R = 2;
let currentR = DEFAULT_R;

X_VALUES = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
Y_MAX = 3;
Y_MIN = -3;

function drawSvg (pointList) {
    $("#svg").empty();
    globalPointsList = pointList;
    console.log("Полученный массив точек: \"" + JSON.stringify(pointList) + "\"");
    CANVAS = SVG()
        .addTo('#svg')
        .size('100%', '100%')
        .viewbox(0, 0, WIDTH, HEIGHT);

    if (pointList === undefined || pointList.length === 0) {
        initSvg();
    } else {
        drawPlotWithPoints(pointList);
    }
}

    function initSvg() {
        console.log('Init plot!');
        CANVAS.rect(WIDTH, HEIGHT).fill(BACKGROUND_COLOR);
        console.log("R value while init:" + currentR);
        drawArea(currentR);
        drawAxes();
        //drawGrid();
        drawAxesScaleLabels(currentR);
        drawRValue(currentR);
    }

    function drawPlotWithPoints(pointList) {
        console.log('Ready to draw plot!');
        let pointsArray = [];
        pointList.forEach(point => {
            pointsArray.push({
                x: point.x,
                y: point.y,
                r: point.r,
                result: point.hit,
            });
        });
        currentR = parseInt(getR());
        if (isNaN(currentR)) {
            lastElementNum = pointsArray.length - 1;
            let lastPoint = pointsArray[pointsArray.length - 1];
            currentR = lastPoint.r;
        }
        console.log('R = ' + currentR);
        drawArea(currentR);

        drawAxes();
        drawAxesScaleLabels(currentR);
        //drawGrid();

        for (let i = 0; i < pointList.length; i++) {
            let point = pointsArray[i];
            drawPoint(point.x, point.y, point.result, pointsScale);
        }
        drawRValue(currentR);
    }

    function convertX(x) {
        return (WIDTH / 2 + x / scale + X_CENTER / scale);
    }

    function convertY(y) {
        return (HEIGHT / 2 - y / scale - Y_CENTER / scale)
    }

    function convertToCoordinatesX(xPoint) {
        return (xPoint - WIDTH / 2) * scale - X_CENTER;
    }

    function convertToCoordinatesY(yPoint) {
        return (HEIGHT / 2 - yPoint) * scale - Y_CENTER;
    }

    function drawAxes() {
        const arrowSize = 10
        //axis x
        CANVAS.line(0, (HEIGHT / 2), WIDTH, (HEIGHT / 2)).stroke({width: 2, color: AXES_COLOR});
        //axis arrow
        const triangleX = (WIDTH - arrowSize) + ',' + (HEIGHT / 2 - arrowSize / 2) + ' ' +
            (WIDTH - arrowSize) + ',' + (HEIGHT / 2 + arrowSize / 2) + ' ' +
            (WIDTH) + ',' + (HEIGHT / 2)
        console.log('x arrow coordinates ' + triangleX)
        CANVAS.polygon(triangleX).fill(AXES_COLOR)
        CANVAS.text('x').font({
            size: 16,
            family: 'Menlo, sans-serif',
            anchor: 'end',
            fill: AXES_COLOR
        }).move(WIDTH - 2 * arrowSize, HEIGHT / 2 - 2 * arrowSize)

        //axis y
        CANVAS.line(WIDTH / 2, 0, WIDTH / 2, HEIGHT).stroke({width: 2, color: AXES_COLOR});
        //axis arrow
        const triangleY = (WIDTH / 2 - arrowSize / 2) + ',' + (arrowSize) + ' ' +
            (WIDTH / 2 + arrowSize / 2) + ',' + (arrowSize) + ' ' +
            (WIDTH / 2) + ',' + (0);
        console.log('y arrow coordinates ' + triangleY)
        CANVAS.polygon(triangleY).fill(AXES_COLOR)
        CANVAS.text('y').font({
            size: 16,
            family: 'Menlo, sans-serif',
            anchor: 'end',
            fill: AXES_COLOR
        }).move(WIDTH / 2 - 1.5 * arrowSize, 1.7 * arrowSize)
    }

    function drawScaleLabel(xStart, xStop, yStart, yStop, labelX, labelY, label) {
        CANVAS.line(convertX(xStart), convertY(yStart), convertX(xStop), convertY(yStop))
            .stroke({width: 2, color: AXES_COLOR});
        CANVAS.text(label).font({
            size: 16,
            family: 'Menlo, sans-serif',
            anchor: 'end',
            fill: AXES_COLOR
        }).move(convertX(labelX), convertY(labelY));
    }

    function drawRValue(r) {
        CANVAS.text('R = ' + r).font({
            size: 16,
            family: 'Menlo, sans-serif',
            anchor: 'end',
            fill: AXES_COLOR
        }).move(WIDTH - 50, HEIGHT - 50);
    }

    function drawAxesScaleLabels(r) {
        console.log('Start drawing axes labels')
        const hatchLen = 0.1;
        console.log("R value while drawing labels: " + r);
        //x axis labels
        drawScaleLabel(-r, -r, hatchLen, -hatchLen, -r, -2 * hatchLen, "-R");
        drawScaleLabel(-r / 2, -r / 2, hatchLen, -hatchLen, -r / 2, -2 * hatchLen, "-R/2");
        drawScaleLabel(r / 2, r / 2, hatchLen, -hatchLen, r / 2, -2 * hatchLen, "R/2");
        drawScaleLabel(r, r, hatchLen, -hatchLen, r, -2 * hatchLen, "R");

        //y axis labels
        drawScaleLabel(hatchLen, -hatchLen, -r, -r, -4 * hatchLen, -r, "-R");
        drawScaleLabel(hatchLen, -hatchLen, -r / 2, -r / 2, -4 * hatchLen, -r / 2, "-R/2");
        drawScaleLabel(hatchLen, -hatchLen, r / 2, r / 2, -4 * hatchLen, r / 2, "R/2");
        drawScaleLabel(hatchLen, -hatchLen, r, r, -4 * hatchLen, r, "R");
    }

    function drawCircle(r) {
        CANVAS.circle(r / scale).fill(AREA_COLOR).move(convertX(-r / 2), convertY(r / 2))
        const fillUnusedCircle = (convertX(0)) + ',' + (convertY(0)) + ' ' +
            (convertX(-r)) + ',' + (convertY(0)) + ' ' +
            (convertX(-r)) + ',' + (convertY(r)) + ' ' +
            (convertX(r)) + ',' + (convertY(r)) + ' ' +
            (convertX(r)) + ',' + (convertY(-r)) + ' ' +
            (convertX(0)) + ',' + (convertY(-r));
        CANVAS.polygon(fillUnusedCircle).fill(BACKGROUND_COLOR)
    }

    function drawRect(r) {
        const area = (convertX(0)) + ',' + (convertY(0)) + ' ' +
            (convertX(-r)) + ',' + (convertY(0)) + ' ' +
            (convertX(-r)) + ',' + (convertY(r / 2)) + ' ' +
            (convertX(0)) + ',' + (convertY(r / 2));
        CANVAS.polygon(area).fill(AREA_COLOR)
    }

    function drawTriangle(r) {
        const area = (convertX(0)) + ',' + (convertY(0)) + ' ' +
            (convertX(0)) + ',' + (convertY(r)) + ' ' +
            (convertX(r)) + ',' + (convertY(0));
        CANVAS.polygon(area).fill(AREA_COLOR)
    }

    function drawArea(r) {
        CANVAS.rect(WIDTH, HEIGHT).fill(BACKGROUND_COLOR);
        drawCircle(r);
        drawRect(r);
        drawTriangle(r);
    }

    function drawPoint(x, y, result, pointScale) {
        let color = result ? '#0f0' : '#f00';
        CANVAS.circle(pointScale).fill(color).move(convertX(x) - pointScale / 2, convertY(y) - pointScale / 2);
    }

    function getCoords(event, element) {
        let coordinates = {};
        let xPosition = element.getBoundingClientRect().left;
        let yPosition = element.getBoundingClientRect().top;
        console.log('xPosition: ' + xPosition + ' X: ' + (event.clientX - xPosition));
        console.log('yPosition: ' + yPosition + ' Y: ' + (event.clientY - yPosition));

        let svg = document.getElementById("svg");
        WIDTH = svg.getBoundingClientRect().width;
        HEIGHT = svg.getBoundingClientRect().height;
        coordinates.x = getAndSetX(convertToCoordinatesX(event.clientX - xPosition));
        coordinates.y = getAndSetY(convertToCoordinatesY(event.clientY - yPosition));
        coordinates.r = parseInt(getR());
        console.log('X: ' + coordinates.x);
        console.log('Y: ' + coordinates.y);
        console.log('R: ' + coordinates.r);
        return coordinates;
    }

    function getR() {
        let result = $('.hidden_r input[type=hidden]').val();
        if (result === 0) {
            return NaN;
        }
        return result;
    }

    function getAndSetX(svgX) {
        let minDiff = Infinity;
        let nearestX;

        for (let i = 0; i < X_VALUES.length; i++) {
            if (Math.abs(svgX - X_VALUES[i]) < minDiff) {
                minDiff = Math.abs(svgX - X_VALUES[i]);
                nearestX = X_VALUES[i];
            }
        }
        let xSelect = $('input[type="radio"][value="' + nearestX + '"]');
        console.log(xSelect.val());
        xSelect.prop('checked', true);

        $('input[type="radio"]').not(xSelect).prop('checked', false);
        return nearestX;
    }

    function getAndSetY(y) {
        let result = y;
        if (y > Y_MAX) {
            result = Y_MAX;
        } else if (y < Y_MIN) {
            result = Y_MIN;
        }
        document.getElementById('input-data:y').value = result;
        return result;
    }

$(function() {
    const fieldForR = $('.hidden_r input[type=hidden]');
    if (currentR === 0) {
        currentR = DEFAULT_R;
    }
    if (fieldForR.val() === "0") {
        fieldForR.val(currentR);
        console.log("Set to hidden input r " + currentR);
    } else {
        currentR = fieldForR.val();
        console.log("Change currentR to " + currentR);
    }
    $(".r_button_" + currentR).addClass("active");

    $('#r-input .r_button').on('click', function(event) {
        $(this).addClass('active');
        $('#r-input .r_button').not(this).removeClass('active');
        currentR = $(this).attr("id").split('').pop();
        $('.hidden_r input[type=hidden]').val(currentR);
        drawSvg(globalPointsList);
    })

    $("#svg").on("click", function (event) {
        console.log('Start drawing point after click! Received coords: ' + event.clientX + ', ' + event.clientY);
        let svg = document.getElementById('svg');
        let coordinates = getCoords(event, svg);
        if (!isNaN(coordinates.r)) {
            console.log('Try to draw point after click. Coordinates: x: ' + coordinates.x + ', y: ' + coordinates.y + ', r: ' + coordinates.r);
            svgAction();
        } else {
            document.getElementById("r-error").classList.remove("hide");
        }
    })
})

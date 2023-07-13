function createGrid() {
    const svgContainer = document.getElementById('graphical-container');
    const svg = document.getElementById('graphical-main');
    const width = svgContainer.clientWidth;
    const height = svgContainer.clientHeight;

    // Set the SVG size
    svg.setAttribute('width', width);
    svg.setAttribute('height', height);

    // Calculate grid dimensions
    const numStations = 9;
    const startY = height * 0.1;
    const endY = height * 0.9;
    const stationSpacing = (endY - startY) / (numStations - 1);
    const startX = width * 0.1;
    const endX = width * 0.9;

    // Add stations and lines
    const stations = ['HKI', 'PSL', 'ILA', 'KHK', 'HPL', 'VMO', 'PJM', 'MÄK', 'LPV'];
    const actualDistances = [0.2, 3.2, 4.4, 4.7, 6.4, 7.5, 8.5, 9.5, 11.2];
    const totalDistance = actualDistances[actualDistances.length - 1];

    // Prepare arrays for storing station y values and time x values
    const stationYValues = [];
    const timeXValues = [];

    for (let i = 0; i < stations.length; i++) {
        const y = endY - (actualDistances[i] / totalDistance) * (endY - startY);
        stationYValues.push(y);

        // Add station text
        const stationText = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        stationText.setAttribute('x', startX - 70);
        stationText.setAttribute('y', y);
        stationText.classList.add('station');
        stationText.textContent = stations[i];
        svg.appendChild(stationText);

        // Add distance text
        const distanceText = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        distanceText.setAttribute('x', startX - 30); // Adjust the padding as needed
        distanceText.setAttribute('y', y);
        distanceText.classList.add('distance');
        distanceText.textContent = `${actualDistances[i]} km`;
        svg.appendChild(distanceText);

        // Add station line
        const stationLine = document.createElementNS('http://www.w3.org/2000/svg', 'line');
        stationLine.setAttribute('x1', startX + 30);
        stationLine.setAttribute('x2', endX);
        stationLine.setAttribute('y1', y);
        stationLine.setAttribute('y2', y);
        stationLine.classList.add('station-line');
        svg.appendChild(stationLine);
    }

    // Calculate the current time, 30 minutes before, and 30 minutes after
    const currentTime = new Date();
    const beforeTime = new Date(currentTime.getTime() - 30 * 60 * 1000);
    const afterTime = new Date(currentTime.getTime() + 30 * 60 * 1000);

    // Calculate the number of seconds
    const numSeconds = Math.ceil((afterTime - beforeTime) / 1000);
    const secondSpacing = (endX - startX - 30) / numSeconds;
    // Add second lines and time labels
    const middleXSecond = startX + 30 + (endX - startX - 30) / 2;
    for (let i = 0; i <= numSeconds; i++) {
        const secondX = middleXSecond + (i - numSeconds / 2) * secondSpacing;
        timeXValues.push(secondX);
    }

    // Calculate the number of ten-minute intervals to display
    const numIntervals = Math.ceil((afterTime - beforeTime) / (10 * 60 * 1000));

    // Calculate the new x-axis spacing
    const tenMinSpacing = (endX - startX - 30) / numIntervals;

    // Add ten-minute lines and time labels
    const middleX = startX + 30 + (endX - startX - 30) / 2;
    for (let i = 0; i <= numIntervals; i++) {
        const tenMinX = middleX + (i - numIntervals / 2) * tenMinSpacing;

        // Calculate the time for this interval
        const intervalTime = new Date(beforeTime.getTime() + i * 10 * 60 * 1000);

        // Add ten-minute line
        const tenMinLine = document.createElementNS('http://www.w3.org/2000/svg', 'line');
        tenMinLine.setAttribute('x1', tenMinX);
        tenMinLine.setAttribute('x2', tenMinX);
        tenMinLine.setAttribute('y1', startY);
        tenMinLine.setAttribute('y2', endY);
        tenMinLine.classList.add('ten-minute-line');
        svg.appendChild(tenMinLine);

        // Add time label
        const timeLabel = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        const timeValue = `${intervalTime.getHours().toString().padStart(2, '0')}:${intervalTime.getMinutes().toString().padStart(2, '0')}`;
        timeLabel.setAttribute('x', tenMinX);
        timeLabel.setAttribute('y', endY + 20);
        timeLabel.setAttribute('text-anchor', 'middle');
        timeLabel.classList.add('time-label');
        timeLabel.textContent = timeValue;
        svg.appendChild(timeLabel);
    }

    // Add current time indicator in the middle of the graph
    const currentTimeLine = document.createElementNS('http://www.w3.org/2000/svg', 'line');
    currentTimeLine.setAttribute('x1', middleX);
    currentTimeLine.setAttribute('x2', middleX);
    currentTimeLine.setAttribute('y1', startY);
    currentTimeLine.setAttribute('y2', endY);
    currentTimeLine.classList.add('current-time');
    svg.appendChild(currentTimeLine);

    return { stationYValues, timeXValues, currentTime };
}

function timeToIndex(currentTime, inputTime) {
    const currentDate = new Date(currentTime);
    const formattedCurrentDate = currentDate.toISOString().slice(0, 10);

    // Create two date objects for inputTime: one for the same day and one for the next day
    const inputSameDay = new Date(`${formattedCurrentDate}T${inputTime}`);
    const nextDay = new Date(currentDate);
    nextDay.setDate(nextDay.getDate() + 1);
    const formattedNextDay = nextDay.toISOString().slice(0, 10);
    const inputNextDay = new Date(`${formattedNextDay}T${inputTime}`);

    // Calculate the time difference (in seconds) for both date objects
    const diffInSecondsSameDay = Math.floor((inputSameDay - currentDate) / 1000);
    const diffInSecondsNextDay = Math.floor((inputNextDay - currentDate) / 1000);

    // Choose the time difference that is closest to the current time and within the valid range
    const diffInSeconds = Math.abs(diffInSecondsNextDay) < Math.abs(diffInSecondsSameDay)
        ? diffInSecondsNextDay
        : diffInSecondsSameDay;

    // Check if the time difference is within the valid range
    if (diffInSeconds < -1800 || diffInSeconds > 1800) {
        return -1; // Return -1 if the time is outside the valid range
    }

    // Calculate the index in the array of 0 to 3600
    const index = diffInSeconds + 1800;

    return index;
}

function drawLinesOnTime(currentTime, timeXValues, stationYValues, trainName, trainThickness, timeValues, stationNamesArray, writeTrainName, trainColor) {
    if (stationNamesArray.length == 0) {
        return
    }
    const stationNames = ['HKI', 'PSL', 'ILA', 'KHK', 'HPL', 'VMO', 'PJM', 'MÄK', 'LPV'];
    const timeIndexes = timeValues.map(time => timeToIndex(currentTime, time));
    const stationIndexes = stationNamesArray.map(name => stationNames.indexOf(name));

    // Create an array of custom x, y points based on time and station values
    const points = timeIndexes.map((timeIndex, i) => {
        return { x: timeXValues[timeIndex], y: stationYValues[stationIndexes[i]] };
    });


    // Convert the points array to a flat array with alternating x and y values
    const flatPoints = points.flatMap(point => [point.x, point.y]);

    // Call drawPolyline with the generated points
    drawPolyline(flatPoints, trainThickness, trainName, writeTrainName, trainColor);
}

// Function to draw polyline
function drawPolyline(points, lineType, lineId, writeTrainName, trainColor) {
    const svg = document.getElementById('graphical-main');
    const polyline = document.createElementNS('http://www.w3.org/2000/svg', 'polyline');
    polyline.setAttribute('points', points.join(','));
    polyline.setAttribute('data-line-id', lineId);
    polyline.setAttribute('stroke', trainColor);
    polyline.classList.add('train-line');
    polyline.classList.add(lineType);
    svg.appendChild(polyline);
    addHoverEffect(polyline);

    if (writeTrainName) {
        var writeOdd = 1;
        // Loop through the points and detect changes in y values
        for (let i = 0; i < points.length - 2; i += 2) {
            const startPoint = [points[i], points[i + 1]];
            const endPoint = [points[i + 2], points[i + 3]];

            if (startPoint[1] !== endPoint[1]) {
                if (writeOdd % 2 == 1) {
                    // Calculate the midpoint and angle of rotation for the segment
                    const { midPoint, angleInDegrees } = getMidPointAndRotation(startPoint, endPoint);

                    // Create a text element and position it at the midpoint of the segment
                    const textElement = createTextElement(lineId, midPoint[0], midPoint[1], trainColor);

                    // Rotate the text element according to the calculated angle
                    textElement.setAttribute('transform', `rotate(${angleInDegrees}, ${midPoint[0]}, ${midPoint[1]})`);

                    svg.appendChild(textElement);
                }
                writeOdd++;
            }
        }
    }
}

function getMidPointAndRotation(startPoint, endPoint) {
    const midPoint = [
        (startPoint[0] + endPoint[0]) / 2,
        (startPoint[1] + endPoint[1]) / 2,
    ];

    const deltaX = endPoint[0] - startPoint[0];
    const deltaY = endPoint[1] - startPoint[1];
    const angleInRadians = Math.atan2(deltaY, deltaX);
    let angleInDegrees = angleInRadians * (180 / Math.PI);

    // Adjust the angle to keep the text upright
    if (Math.abs(angleInDegrees) > 90) {
        angleInDegrees -= 180;
    }

    return { midPoint, angleInDegrees };
}

function createTextElement(text, x, y, color) {
    const offset = -3;
    const textElement = document.createElementNS('http://www.w3.org/2000/svg', 'text');
    textElement.textContent = text;
    textElement.setAttribute('x', x);
    textElement.setAttribute('y', y + offset);
    textElement.setAttribute('fill', color);
    textElement.classList.add('line-id-text'); // Add a class for styling purposes

    // Add the data-line-id attribute to the text element
    textElement.setAttribute('data-line-id', text);

    return textElement;
}

// Function to clear the existing grid elements
function clearGrid(svg) {
    while (svg.lastChild) {
        svg.removeChild(svg.lastChild);
    }
}

function addHoverEffect(polyline) {
    polyline.addEventListener('mouseenter', (event) => {
        const lineId = event.target.getAttribute('data-line-id');
        const elements = document.querySelectorAll(`[data-line-id="${lineId}"]`);
        elements.forEach((element) => {
            element.classList.add('highlighted');
        });

        // Include <text> elements with the same value of lineId
        const textElements = document.querySelectorAll(`text[data-line-id="${lineId}"]`);
        textElements.forEach((textElement) => {
            textElement.classList.add('highlighted-text');
        });
    });

    polyline.addEventListener('mouseleave', (event) => {
        const lineId = event.target.getAttribute('data-line-id');
        const elements = document.querySelectorAll(`[data-line-id="${lineId}"]`);
        elements.forEach((element) => {
            element.classList.remove('highlighted');
        });

        // Include <text> elements with the same value of lineId
        const textElements = document.querySelectorAll(`text[data-line-id="${lineId}"]`);
        textElements.forEach((textElement) => {
            textElement.classList.remove('highlighted-text');
        });
    });
}

function plotData(data) {
    const svg = document.getElementById('graphical-main');
    clearGrid(svg);
    const gridValues = createGrid();
    data.forEach(train => {
        const actualTimesLength = train.actualTimes.length;
        var writeTrainName = true;
        if (actualTimesLength > 0) {
            drawLinesOnTime(gridValues.currentTime, gridValues.timeXValues, gridValues.stationYValues, train.trainNumber, 'thick', train.actualTimes, train.actualStationCodes, writeTrainName, train.trainColor);
            if (actualTimesLength > 2) {
                writeTrainName = false;
            }
        }
        drawLinesOnTime(gridValues.currentTime, gridValues.timeXValues, gridValues.stationYValues, train.trainNumber, 'thin', train.scheduledTimes, train.scheduledStationCodes, writeTrainName, train.trainColor);
    });

}

function fetchTrainData() {
    return fetch('/live-trains')
        .then(response => response.json())
        .then(data => {
            return data;
        });
}

document.addEventListener('DOMContentLoaded', () => {

    async function fetchDataAndPlot() {
        const data = await fetchTrainData();

        // Create a function to call plotData with the fetched data
        const plotFetchedData = () => plotData(data);

        plotFetchedData(); // Plot the data initially
        window.addEventListener('resize', plotFetchedData); // Attach the event listener
    }

    const flipGridButton = document.getElementById('flip-grid-button');
    const svgWrapper = document.getElementById('svg-wrapper');
    const graphicalContainer = document.getElementById('graphical-container');
    const svg = document.getElementById('graphical-main');

    let isGridFlipped = false;
    flipGridButton.addEventListener('click', () => {
        isGridFlipped = !isGridFlipped;
        svgWrapper.classList.toggle('flip-grid');

        if (isGridFlipped) {
            svgWrapper.style.width = `${graphicalContainer.offsetHeight}px`;
            svgWrapper.style.height = `${graphicalContainer.offsetWidth}px`;
            graphicalContainer.style.justifyContent = 'flex-start';
        } else {
            svgWrapper.style.width = '100%';
            svgWrapper.style.height = '100%';
            graphicalContainer.style.justifyContent = 'center';
        }
    });

    // Fetch data and plot initially
    fetchDataAndPlot();

    // Set an interval to fetch data and plot every 10 seconds
    setInterval(() => fetchDataAndPlot(), 10 * 1000);
});
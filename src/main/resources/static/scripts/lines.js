import { createSVGElement, timeToIndex } from './helpers.js';

export function drawLinesOnTime(currentTime, timeXValues, stationYValues, trainName, trainThickness, timeValues, stationNamesArray, writeTrainName, trainColor) {
  if (stationNamesArray.length === 0) {
    return;
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

function drawPolyline(points, lineType, lineId, writeTrainName, trainColor) {
  const svg = document.getElementById('graphical-main');
  const polyline = createSVGElement('polyline', {
    points: points.join(','),
    'data-line-id': lineId,
    stroke: trainColor
  });
  polyline.classList.add('train-line', lineType);
  svg.appendChild(polyline);

  if (writeTrainName) {
    let writeOdd = 1;
    // Loop through the points and detect changes in y values
    for (let i = 0; i < points.length - 2; i += 2) {
      const startPoint = [points[i], points[i + 1]];
      const endPoint = [points[i + 2], points[i + 3]];

      if (startPoint[1] !== endPoint[1]) {
        if (writeOdd % 2 === 1) {
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
  addHoverEffectLine(polyline, lineId);
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
  const textElement = createSVGElement('text', {
    text,
    x,
    y: y + offset,
    fill: color,
    'data-line-id': text,
  }, 'line-id-text', text);
  textElement.style.cursor = 'pointer';
  return textElement;
}

function applyHoverEffect(lineId) {
  const elements = document.querySelectorAll(`[data-line-id="${lineId}"]`);
  elements.forEach((element) => {
    element.classList.add('highlighted');
  });

  const textElements = document.querySelectorAll(`text[data-line-id="${lineId}"]`);
  textElements.forEach((textElement) => {
    textElement.classList.add('highlighted-text');
  });
}

function removeHoverEffect(lineId) {
  const elements = document.querySelectorAll(`[data-line-id="${lineId}"]`);
  elements.forEach((element) => {
    element.classList.remove('highlighted');
  });

  const textElements = document.querySelectorAll(`text[data-line-id="${lineId}"]`);
  textElements.forEach((textElement) => {
    textElement.classList.remove('highlighted-text');
  });
}

function addHoverEffectLine(polyline, lineId) {
  polyline.addEventListener('mouseenter', () => {
    applyHoverEffect(lineId);
  });

  polyline.addEventListener('mouseleave', () => {
    removeHoverEffect(lineId);
  });

  const textElements = document.querySelectorAll(`text[data-line-id="${lineId}"]`);
  textElements.forEach((textElement) => {
    textElement.addEventListener('mouseenter', () => {
      applyHoverEffect(lineId);
    });

    textElement.addEventListener('mouseleave', () => {
      removeHoverEffect(lineId);
    });
  });
}
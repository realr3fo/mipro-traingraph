import { createSVGElement, showTooltip, hideTooltip } from './helpers.js';

export function createGrid() {
  const svgContainer = document.getElementById('graphical-container');
  const svg = document.getElementById('graphical-main');
  const width = svgContainer.clientWidth;
  const height = svgContainer.clientHeight;

  // Set the SVG size
  svg.setAttribute('width', width);
  svg.setAttribute('height', height);

  // Calculate grid dimensions
  const startY = height * 0.1;
  const endY = height * 0.9;
  const startX = width * 0.1;
  const endX = width * 0.9;

  const dataElement = document.getElementById("data");
  const stations = JSON.parse(dataElement.dataset.stations);
  const fullStationNames = JSON.parse(dataElement.dataset.fullStationNames);
  const actualDistances = JSON.parse(dataElement.dataset.actualDistances);
  const totalDistance = actualDistances[actualDistances.length - 1];

  // Prepare arrays for storing station y values and time x values
  const stationYValues = [];
  const timeXValues = [];

  for (let i = 0; i < stations.length; i++) {
    const y = endY - (actualDistances[i] / totalDistance) * (endY - startY);
    stationYValues.push(y);

    // Add station text
    const stationText = createSVGElement('text', {
      x: startX - 70,
      y: y,
    }, 'station', stations[i]);
    svg.appendChild(stationText);
    stationText.addEventListener('mouseover', (e) => showTooltip(e, fullStationNames[i]));
    stationText.addEventListener('mouseout', hideTooltip);
    stationText.style.cursor = 'pointer';

    // Add distance text
    const distanceText = createSVGElement('text', {
      x: startX - 30,
      y: y,
    }, 'distance', `${actualDistances[i]} km`);
    svg.appendChild(distanceText);

    // Add station line
    const stationLine = createSVGElement('line', {
      x1: startX + 30,
      x2: endX,
      y1: y,
      y2: y,
    }, 'station-line');
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
    const tenMinLine = createSVGElement('line', {
      x1: tenMinX,
      x2: tenMinX,
      y1: startY,
      y2: endY,
    }, 'ten-minute-line');
    svg.appendChild(tenMinLine);

    // Add time label
    const timeLabel = createSVGElement('text', {
      x: tenMinX,
      y: endY + 20,
      'text-anchor': 'middle',
    }, 'time-label', `${intervalTime.getHours().toString().padStart(2, '0')}:${intervalTime.getMinutes().toString().padStart(2, '0')}`);
    svg.appendChild(timeLabel);
  }

  // Add current time indicator in the middle of the graph
  const currentTimeLine = createSVGElement('line', {
    x1: middleX,
    x2: middleX,
    y1: startY,
    y2: endY,
  }, 'current-time');
  svg.appendChild(currentTimeLine);

  return { stationYValues, timeXValues, currentTime };
}
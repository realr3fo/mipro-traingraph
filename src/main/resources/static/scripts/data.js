import { clearGrid, hideTooltip } from './helpers.js';
import { createGrid } from './grid.js';
import { drawLinesOnTime } from './lines.js'

export async function fetchDataAndPlot() {
  hideTooltip();
  const data = await fetchTrainData();

  // Create a function to call plotData with the fetched data
  const plotFetchedData = () => plotData(data);

  plotFetchedData(); // Plot the data initially
  window.addEventListener('resize', plotFetchedData); // Attach the event listener
}

async function fetchTrainData() {
  const response = await fetch('/live-trains');
  return response.json();
}

function plotData(data) {
  const svg = document.getElementById('graphical-main');
  clearGrid(svg);
  const gridValues = createGrid();

  data.forEach(train => {
    const { actualTimes, scheduledTimes, actualStationCodes, scheduledStationCodes, trainNumber, trainColor } = train;
    const actualTimesLength = actualTimes.length;
    let writeTrainName = true;
    if (actualTimesLength > 0) {
      drawLinesOnTime(gridValues.currentTime, gridValues.timeXValues, gridValues.stationYValues, trainNumber, 'thick', actualTimes, actualStationCodes, writeTrainName, trainColor);
      if (actualTimesLength > 2) {
        writeTrainName = false;
      }
    }
    drawLinesOnTime(gridValues.currentTime, gridValues.timeXValues, gridValues.stationYValues, trainNumber, 'thin', scheduledTimes, scheduledStationCodes, writeTrainName, trainColor);
  });
}

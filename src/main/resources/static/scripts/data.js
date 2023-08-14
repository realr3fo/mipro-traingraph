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
  try {
    const response = await fetch('/live-trains');
    if (!response.ok) {
      throw { status: response.status, message: response.statusText };
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error:', error);
    if (error instanceof TypeError && error.message === 'Failed to fetch') {
      alert('Connection refused. Please check your internet connection and try again.');
    } else {
      alert('Too many requests. Please wait and try again later.');
    }
  }
}

function plotData(data) {
  const svg = document.getElementById('graphical-main');
  clearGrid(svg);
  const gridValues = createGrid();

  data.forEach(train => {
    const { actualTimes, scheduledTimes, actualStationCodes, scheduledStationCodes, trainNumber, trainColor } = train;
    const actualTimesLength = actualTimes.length;
    let writeTrainName = true;
    const drawLinesObject = {
      currentTime: gridValues.currentTime,
      timeXValues: gridValues.timeXValues,
      stationYValues: gridValues.stationYValues,
      trainName: trainNumber,
      trainThickness: '',
      timeValues: [],
      stationNamesArray: [],
      writeTrainName: writeTrainName,
      trainColor: trainColor,
    };
    if (actualTimesLength > 0) {
      drawLinesObject.trainThickness = 'thick';
      drawLinesObject.timeValues = actualTimes;
      drawLinesObject.stationNamesArray = actualStationCodes;
      drawLinesOnTime(drawLinesObject);
      if (actualTimesLength > 2) {
        drawLinesObject.writeTrainName = false;
      }
    }
    drawLinesObject.trainThickness = 'thin';
    drawLinesObject.timeValues = scheduledTimes;
    drawLinesObject.stationNamesArray = scheduledStationCodes;
    drawLinesOnTime(drawLinesObject);
  });
}
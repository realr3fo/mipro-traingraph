import { fetchDataAndPlot } from './data.js';

document.addEventListener('DOMContentLoaded', () => {
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

  fetchDataAndPlot();
  setInterval(fetchDataAndPlot, 10 * 1000);
});

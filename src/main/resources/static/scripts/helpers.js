export function createSVGElement(tagName, attributes, className = 'none', textContent = '') {
  const element = document.createElementNS('http://www.w3.org/2000/svg', tagName);
  for (const [attr, value] of Object.entries(attributes)) {
    element.setAttribute(attr, value);
  }
  element.textContent = textContent;
  element.classList.add(className);
  return element;
}

export function timeToIndex(currentTime, inputTime) {
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

export function clearGrid(svg) {
  while (svg.lastChild) {
    svg.removeChild(svg.lastChild);
  }
}


let tooltip;

export function showTooltip(event, text) {
  tooltip = document.createElement('div');
  tooltip.textContent = text;
  tooltip.style.position = 'absolute';
  tooltip.style.background = '#f9f9f9';
  tooltip.style.border = '1px solid #c0c0c0';
  tooltip.style.borderRadius = '4px';
  tooltip.style.padding = '4px 8px';
  tooltip.style.fontSize = '14px';
  tooltip.style.whiteSpace = 'nowrap';
  tooltip.style.fontFamily = 'Open sans';
  tooltip.style.top = `${event.clientY + 10}px`;
  tooltip.style.left = `${event.clientX + 10}px`;

  document.body.appendChild(tooltip);
}

export function hideTooltip() {
  if (tooltip) {
    document.body.removeChild(tooltip);
    tooltip = null;
  }
}
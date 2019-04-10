/**
 * this function makes only what class of a given checkbox selected at a time
 * 
 * @param obj the object selected
 * @param name the class of objects to be deselected
 * @returns
 */
function checkBoxUpdate(obj, name) {
	var cbs = document.getElementsByClassName(name);
	for (var i = 0; i < cbs.length; i++) {
		cbs[i].checked = false;
	}
	obj.checked = true;
}

/**
 * this function checks all other checkboxes on a page given a source
 * 
 * @param source the checkbox the checks all other boxes
 * @returns
 */
function toggle(source) {
	var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i] != source)
			checkboxes[i].checked = source.checked;
	}
}

function validateCheckboxes(name) {
	var checkBoxes = document.getElementsByClassName(name);
	var isChecked = false;
	for (var i = 0; i < checkBoxes.length; i++) {
		if (checkBoxes[i].checked) {
			isChecked = true;
		}
		;
	}
	;
	if (isChecked) {
		alert('At least one checkbox checked!');
	} else {
		alert('Please, check at least one checkbox!');
	}
}
/**
 * this function makes only what class of a given checkbox selected at a time
 * 
 * @param obj
 *            the object selected
 * @param name
 *            the class of objects to be deselected
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
 * this function checks all other checkboxes on a page given a element
 * 
 * @param element
 *            the checkbox the checks all other boxes
 * @returns
 */
function toggle(element) {
	var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i] != element)
			checkboxes[i].checked = element.checked;
	}
}

/**
 * this function checks each questions answers, making sure one is checked
 * 
 * @returns true if every question has an answer
 */
function validateCheckboxes() {

	var inputs = document.getElementsByTagName('input');

	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == 'checkbox') {
			var question_boxes = document.getElementsByClassName(inputs[i].className);
			var valid = false;
			for (var x = 0; x < question_boxes.length; x++) {
				if(question_boxes[x].checked == true){
					valid = true;
				}
			}
			if(valid == false){
				alert('Not all questions have been answered.');
				return false;
			}
		}
	}
	
	return true;
}
function getChromeVersion() {
	ajaxAsyncRequest("get-chrome-version/");
}
function ajaxAsyncRequest(reqURL) {
	var raw = navigator.userAgent.match(/Chrom(e|ium)\/([0-9]+)\./);
	var chromeVersion = parseInt(raw[2], 10);
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	console.log(reqURL + "versionNumber=" + chromeVersion);
	xmlhttp.open("GET", reqURL + "chromeVersion=" + chromeVersion, true);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				console.log(xmlhttp.responseText);
			} else {
				console.log("Something is wrong !!");
			}
		}
	};
	xmlhttp.send(chromeVersion);
}
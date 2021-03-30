document.getElementById('fl_inp').onchange = function changeDiv() {
    let fullPath = String(document.getElementById('fl_inp').value);
    if (fullPath) {
        let startIndex = (fullPath.indexOf('\\') >= 0
            ? fullPath.lastIndexOf('\\')
            : fullPath.lastIndexOf('/'));
        let filename = fullPath.substring(startIndex);
        if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
            filename = filename.substring(1);
        }
        let div = document.getElementById('file_name');
        div.innerText = filename;
    }
};
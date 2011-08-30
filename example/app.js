Titanium.MillennialMedia = Ti.MillennialMedia = require("ti.millennialmedia");

var win = Ti.UI.createWindow({
    backgroundColor: 'white'
});
win.add(Ti.MillennialMedia.createView({
    top: 0, left: 0,
    width: 320,
    height: 53,
    backgroundColor: 'red'
}));
win.open();
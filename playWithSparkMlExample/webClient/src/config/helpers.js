/**
 * Created by yingding on 04.04.17.
 * This is deprecated and not used in the current seed anymore
 */
var path = require('path');
var _root = path.resolve(__dirname, '..');
function root(args) {
    args = Array.prototype.slice.call(arguments, 0);
    return path.join.apply(path, [_root].concat(args));
}
exports.root = root;

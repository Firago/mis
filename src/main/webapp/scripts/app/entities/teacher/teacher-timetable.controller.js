'use strict';

var intervals = [{
    start: 450,
    end: 540,
    type: 'lesson'
}, {
    start: 540,
    end: 555,
    type: 'break'
}, {
    start: 555,
    end: 645,
    type: 'lesson'
}, {
    start: 645,
    end: 660,
    type: 'break'
}, {
    start: 660,
    end: 750,
    type: 'lesson'
}, {
    start: 750,
    end: 765,
    type: 'break'
}, {
    start: 765,
    end: 855,
    type: 'lesson'
}, {
    start: 855,
    end: 870,
    type: 'break'
}, {
    start: 870,
    end: 960,
    type: 'lesson'
}, {
    start: 960,
    end: 975,
    type: 'break'
}, {
    start: 975,
    end: 1065,
    type: 'lesson'
}, {
    start: 1065,
    end: 1080,
    type: 'break'
}, {
    start: 1080,
    end: 1170,
    type: 'lesson'
}, {
    start: 1170,
    end: 1175,
    type: 'break'
}, {
    start: 1175,
    end: 1265,
    type: 'lesson'
}];

angular.module('misApp')
    .controller('TeacherTimetableController', function ($scope, $rootScope, $stateParams, Teacher) {

        $scope.loading = true;

        var dayStart = (60 * 7) + 30;
        var dayEnd = (60 * 21) + 5;
        var stepDuration = 5;
        var columnWidth = 230;
        $scope.cellHeight = 9;
        $scope.sliderOffset = 0;

        $scope.content = {
            intervals: intervals,
            columns: []
        };

        intervals.forEach(function (interval) {
            interval.height = ((interval.end - interval.start) / stepDuration) * $scope.cellHeight;
        });

        Teacher.timetable({id: $stateParams.id}, {}, function (result) {

            $scope.sliderWidth = result.days.length * columnWidth;

            $scope.left = function () {
                var newOffset = $scope.sliderOffset + columnWidth;
                if (newOffset > 0) {
                    $scope.sliderOffset = 0;
                } else {
                    $scope.sliderOffset = newOffset;
                }
            };

            $scope.right = function () {
                var wrapperWidth = document.getElementById('wrapper').clientWidth;
                var newOffset = $scope.sliderOffset - columnWidth;
                if (newOffset + $scope.sliderWidth < wrapperWidth) {
                    $scope.sliderOffset = wrapperWidth - $scope.sliderWidth - 135;
                } else {
                    $scope.sliderOffset = newOffset;
                }
            };

            String.prototype.paddingLeft = function (paddingValue) {
                return String(paddingValue + this).slice(-paddingValue.length);
            };

            $scope.getFormattedTime = function (time) {
                var hours = Math.floor(time / 60);
                var minutes = time - (hours * 60);
                hours = hours.toString().paddingLeft('00');
                minutes = minutes.toString().paddingLeft('00');
                return hours + '.' + minutes;
            };

            $scope.resolveColor = function (time) {
                for (var i = 0; i < intervals.length; i++) {
                    var interval = intervals[i];
                    if (time >= interval.start && time < interval.end) {
                        return interval.type == 'lesson' ? '#C5CAE9' : '#E8EAF6';
                    }
                }
            };

            $scope.loadData = function (timetable) {
                timetable.days.forEach(function (day) {
                    var pos = dayStart;
                    var entries = [];
                    day.lessons.forEach(function (lesson) {
                        while (pos < lesson.end) {
                            if (pos == lesson.start) {
                                entries.push({
                                    title: lesson.title,
                                    type: lesson.type,
                                    start: lesson.start,
                                    end: lesson.end,
                                    height: ((lesson.end - lesson.start) / stepDuration) * $scope.cellHeight,
                                    with: lesson.with,
                                    room: lesson.room,
                                    color: $scope.resolveColor(pos)
                                });
                            } else {
                                entries.push({
                                    title: null,
                                    height: $scope.cellHeight,
                                    color: $scope.resolveColor(pos)
                                });
                            }
                            pos += stepDuration;
                        }
                    });
                    while (pos < dayEnd) {
                        entries.push({
                            title: null,
                            height: $scope.cellHeight,
                            color: $scope.resolveColor(pos)
                        });
                        pos += stepDuration;
                    }
                    $scope.content.columns.push({
                        header: day.title + ' (' + day.date.substring(0, 10) + ')',
                        width: columnWidth,
                        entries: entries
                    });
                });
            }

            $scope.loadData(result);

            $scope.loading = false;

        });
    });

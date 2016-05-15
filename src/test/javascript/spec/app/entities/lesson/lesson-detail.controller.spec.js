'use strict';

describe('Controller Tests', function() {

    describe('Lesson Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLesson, MockSubjectEntry, MockTeacher, MockStudentGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLesson = jasmine.createSpy('MockLesson');
            MockSubjectEntry = jasmine.createSpy('MockSubjectEntry');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockStudentGroup = jasmine.createSpy('MockStudentGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Lesson': MockLesson,
                'SubjectEntry': MockSubjectEntry,
                'Teacher': MockTeacher,
                'StudentGroup': MockStudentGroup
            };
            createController = function() {
                $injector.get('$controller')("LessonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'misApp:lessonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

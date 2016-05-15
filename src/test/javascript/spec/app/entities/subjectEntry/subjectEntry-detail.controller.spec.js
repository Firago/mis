'use strict';

describe('Controller Tests', function() {

    describe('SubjectEntry Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubjectEntry, MockSubject, MockTeacher, MockStudentGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubjectEntry = jasmine.createSpy('MockSubjectEntry');
            MockSubject = jasmine.createSpy('MockSubject');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockStudentGroup = jasmine.createSpy('MockStudentGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubjectEntry': MockSubjectEntry,
                'Subject': MockSubject,
                'Teacher': MockTeacher,
                'StudentGroup': MockStudentGroup
            };
            createController = function() {
                $injector.get('$controller')("SubjectEntryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'misApp:subjectEntryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

'use strict';

describe('Controller Tests', function() {

    describe('Teacher Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTeacher, MockUser, MockSubject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockUser = jasmine.createSpy('MockUser');
            MockSubject = jasmine.createSpy('MockSubject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Teacher': MockTeacher,
                'User': MockUser,
                'Subject': MockSubject
            };
            createController = function() {
                $injector.get('$controller')("TeacherDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'misApp:teacherUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

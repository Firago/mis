'use strict';

describe('Controller Tests', function() {

    describe('Student Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudent, MockUser, MockStudentGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudent = jasmine.createSpy('MockStudent');
            MockUser = jasmine.createSpy('MockUser');
            MockStudentGroup = jasmine.createSpy('MockStudentGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Student': MockStudent,
                'User': MockUser,
                'StudentGroup': MockStudentGroup
            };
            createController = function() {
                $injector.get('$controller')("StudentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'misApp:studentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

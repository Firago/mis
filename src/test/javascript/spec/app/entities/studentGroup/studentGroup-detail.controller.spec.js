'use strict';

describe('Controller Tests', function() {

    describe('StudentGroup Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudentGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudentGroup = jasmine.createSpy('MockStudentGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'StudentGroup': MockStudentGroup
            };
            createController = function() {
                $injector.get('$controller')("StudentGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'misApp:studentGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

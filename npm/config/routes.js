exports['default'] = {
  routes: function (api) {
    return {
      get: [
        // Device Types
        { path: '/devicetypes', action: 'retrieveDeviceTypes' },
        { path: '/devicetypes/:deviceTypeId', action: 'retrieveDeviceType' },
        // Rooms
        { path: '/rooms', action: 'retrieveRooms' },
        { path: '/rooms/:roomId', action: 'retrieveRoom' },
        // Devices
        { path: '/devices', action: 'retrieveDevices' },
        { path: '/devices/logs/limit/:limit/offset/:offset', action: 'retrieveDevicesLogs' },
        { path: '/devices/events', action: 'retrieveDevicesEvents' },
        { path: '/devices/:deviceId', action: 'retrieveDevice' },
        { path: '/devices/:deviceId/events', action: 'retrieveDeviceEvents' },
        { path: '/devices/:deviceId/logs/limit/:limit/offset/:offset', action: 'retrieveDeviceLogs' },
        { path: '/devices/devicetypes/:deviceTypeId', action: 'retrieveDeviceTypeDevices' },
        // Room Devices
        { path: '/rooms/:roomId/devices', action: 'retrieveRoomDevices' },
        // Routines
        { path: '/routines', action: 'retrieveRoutines' },
        { path: '/routines/:routineId', action: 'retrieveRoutine' },
        // Other
        { path: '/swagger', action: 'swagger' }
      ],

      post: [
        // Rooms
        { path: '/rooms', action: 'createRoom' },
        // Devices
        { path: '/devices', action: 'createDevice' },
        // Room Devices
        { path: '/devices/:deviceId/rooms/:roomId', action: 'addDeviceRoom' },
        // Routines
        { path: '/routines', action: 'createRoutine' }
      ],

      put: [
        // Rooms
        { path: '/rooms/:roomId', action: 'updateRoom' },
        // Devices
        { path: '/devices/:deviceId', action: 'updateDevice' },
        { path: '/devices/:deviceId/:actionName', action: 'executeDeviceAction' },
        // Routines
        { path: '/routines/:routineId', action: 'updateRoutine' },
        { path: '/routines/:routineId/execute', action: 'executeRoutine' }
      ],

      delete: [
        // Rooms
        { path: '/rooms/:roomId', action: 'deleteRoom' },
        // Devices
        { path: '/devices/:deviceId', action: 'deleteDevice' },
        // Room Devices
        { path: '/devices/:deviceId/rooms', action: 'deleteDeviceRoom' },
        // Routines
        { path: '/routines/:routineId', action: 'deleteRoutine' }
      ]
 
      /* ---------------------
      routes.js

      For web clients (http and https) you can define an optional RESTful mapping to help route requests to actions.
      If the client doesn't specify and action in a param, and the base route isn't a named action, the action will attempt to be discerned from this routes.js file.

      Learn more here: http://www.actionherojs.com/docs/#routes

      examples:

      get: [
        { path: '/users', action: 'usersList' }, // (GET) /api/users
        { path: '/search/:term/limit/:limit/offset/:offset', action: 'search' }, // (GET) /api/search/car/limit/10/offset/100
      ],

      post: [
        { path: '/login/:userID(^\\d{3}$)', action: 'login' } // (POST) /api/login/123
      ],

      all: [
        { path: '/user/:userID', action: 'user', matchTrailingPathParts: true } // (*) /api/user/123, api/user/123/stuff
      ]

      ---------------------- */
    }
  }
}

<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_title">
        <f:message key="general.label.home"/>
    </s:layout-component>
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/fullcalendar.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/fullcalendar.print.css" type="text/css" media="print" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/compiled/calendar.css" type="text/css" media="screen" />
    </s:layout-component>
    <s:layout-component name="page_header">
        <f:message key="general.label.dashboard"/> - ${sessionScope.userSession.fullname}
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1><f:message key="general.label.dashboard"/></h1>
            <c:if test="${sessionScope.userSession.id == '0'}">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="main-box clearfix">
                            <div class="clearfix">
                                <h2 class="pull-left">Unregistered User</h2>
                            </div>
                            <hr/>
                            <p>Sorry, your ID is not registered with HMS, please contact our System Administrator for further help!</p>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.userSession.id != '0'}">
                <div class="row">
                    <div class="main-box">
                        <div id="calendar"></div>
                    </div>
                </div>
            </c:if>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/jquery-ui.custom.min.js"></script>
        <script src="${contextPath}/resources/private/js/fullcalendar.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
            <c:if test="${sessionScope.userSession.id != '0'}">
                /* initialize the external events
                 -----------------------------------------------------------------*/

                $('#external-events div.external-event').each(function () {

                    // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
                    // it doesn't need to have a start or end
                    var eventObject = {
                        title: $.trim($(this).text()) // use the element's text as the event title
                    };

                    // store the Event Object in the DOM element so we can get to it later
                    $(this).data('eventObject', eventObject);

                    // make the event draggable using jQuery UI
                    $(this).draggable({
                        zIndex: 999,
                        revert: true, // will cause the event to go back to its
                        revertDuration: 0  //  original position after the drag
                    });

                });


                /* initialize the calendar
                 -----------------------------------------------------------------*/

                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();

                var calendar = $('#calendar').fullCalendar({
                    contentHeight: 600,
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    selectable: true,
                    selectHelper: true,
                    select: function (start, end, allDay) {
                        var title = prompt('Event Title:');
                        if (title) {
                            calendar.fullCalendar('renderEvent',
                                    {
                                        title: title,
                                        start: start,
                                        end: end,
                                        allDay: allDay
                                    },
                                    true // make the event "stick"
                                    );
                        }
                        calendar.fullCalendar('unselect');
                    },
                    editable: true,
                    droppable: true, // this allows things to be dropped onto the calendar !!!
                    drop: function (date, allDay) { // this function is called when something is dropped

                        // retrieve the dropped element's stored Event Object
                        var originalEventObject = $(this).data('eventObject');

                        // we need to copy it, so that multiple events don't have a reference to the same object
                        var copiedEventObject = $.extend({}, originalEventObject);

                        // assign it the date that was reported
                        copiedEventObject.start = date;
                        copiedEventObject.allDay = allDay;

                        // copy label class from the event object
                        var labelClass = $(this).data('eventclass');

                        if (labelClass) {
                            copiedEventObject.className = labelClass;
                        }

                        // render the event on the calendar
                        // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                        $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

                        // is the "remove after drop" checkbox checked?
                        if ($('#drop-remove').is(':checked')) {
                            // if so, remove the element from the "Draggable Events" list
                            $(this).remove();
                        }

                    },
                    buttonText: {
                        prev: '<i class="fa fa-chevron-left"></i>',
                        next: '<i class="fa fa-chevron-right"></i>'
                    },
                    events: [
                <c:forEach items="${courseRegisterList}" var="courseRegister">
                        {
                            title: '${courseRegister.courseCode} - ${courseRegister.title}',
                                                start: '${courseRegister.startDate}',
                                                end: '${courseRegister.endDate}',
                                                className: '${courseRegister.calendarLabel}'
                                            },
                </c:forEach>
                                        ]
                                    });
            </c:if>
                                });
        </script>
    </s:layout-component>
</s:layout-render>
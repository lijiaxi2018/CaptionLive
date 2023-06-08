import { Sidenav, Nav, Toggle } from 'rsuite';

const SidebarElement = ({
    id,
    eventKey,
    href='',
    name,
    onClick,
}) => {
    if (id === undefined || name === undefined) {
        /** an side bar tab should not miss a key or name */
        return (
            <Nav.Item key={id} eventKey={eventKey}>
                missing id or name
            </Nav.Item>
        );
    } else {
        return (
            <Nav.Item key={`${id}-${id}`} eventKey={eventKey} href={href} onClick={onClick}>
                <a href={href}>{name}</a>
            </Nav.Item>
        );
    }
}

export default SidebarElement
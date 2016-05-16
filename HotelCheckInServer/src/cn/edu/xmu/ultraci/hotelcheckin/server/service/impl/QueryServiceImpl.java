package cn.edu.xmu.ultraci.hotelcheckin.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.ErrorCode;
import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.ICheckinDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IFloorDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IGuestDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IMemberDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IRoomDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.ITypeDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.BaseDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.FloorDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.InfoDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.MemberDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.RoomDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.StatusDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.TypeDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.factory.BaseFactory;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.CheckinPO;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.FloorPO;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.GuestPO;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.MemberPO;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.RoomPO;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.TypePO;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IConfService;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IQueryService;
import cn.edu.xmu.ultraci.hotelcheckin.server.util.StringUtil;

public class QueryServiceImpl implements IQueryService {

	private static Logger logger = LogManager.getLogger();

	@Override
	public BaseDTO queryMember(Map<String, String> params) {
		String device = params.get("device");
		String cardid = params.get("cardid");
		if (!StringUtil.isBlank(device) && !StringUtil.isBlank(cardid)) {
			IMemberDao memberDao = (IMemberDao) BaseFactory.getInstance(IMemberDao.class);
			MemberPO member = memberDao.retrieveMemberByCardId(cardid);
			if (member != null) {
				MemberDTO relModel = new MemberDTO();
				relModel.setId(member.getId());
				// 返回会员信息时隐藏部分敏感信息
				relModel.setName(StringUtil.shieldPartionStr(member.getName(), 1, 1));
				relModel.setIdcard(StringUtil.shieldPartionStr(member.getIdcard(), 6, 13));
				relModel.setMobile(StringUtil.shieldPartionStr(member.getMobile(), 3, 6));
				logger.info(String.format(LogTemplate.QUERY_MEMBER_OK, device, cardid));
				return relModel;
			} else {
				logger.warn(String.format(LogTemplate.QUERY_MEMBER_NO_SUCH_CARD, device, cardid));
				return new BaseDTO(ErrorCode.QUERY_MEMBER_NO_SUCH_CARD);
			}
		} else {
			logger.warn(String.format(LogTemplate.INVALID_PARAMS, params));
			return new BaseDTO(ErrorCode.INVALID_REQ);
		}
	}

	@Override
	public BaseDTO queryType(Map<String, String> params) {
		String device = params.get("device");
		if (!StringUtil.isBlank(device)) {
			TypeDTO retModel = new TypeDTO();
			ITypeDao typeDao = (ITypeDao) BaseFactory.getInstance(ITypeDao.class);
			List<TypePO> types = typeDao.retrieveAllType();
			if (types != null) {
				for (TypePO type : types) {
					retModel.addType(type.getId(), type.getName(), type.getDeposit(),
							type.getPrice());
				}
				logger.info(String.format(LogTemplate.QUERY_TYPE_OK, device, types.size()));
			}
			return retModel;
		} else {
			logger.warn(String.format(LogTemplate.INVALID_PARAMS, params));
			return new BaseDTO(ErrorCode.INVALID_REQ);
		}
	}

	@Override
	public BaseDTO queryFloor(Map<String, String> params) {
		String device = params.get("device");
		if (!StringUtil.isBlank(device)) {
			FloorDTO retModel = new FloorDTO();
			IFloorDao floorDao = (IFloorDao) BaseFactory.getInstance(IFloorDao.class);
			List<FloorPO> floors = floorDao.retrieveAllFloor();
			if (floors != null) {
				for (FloorPO floor : floors) {
					retModel.addFloor(floor.getId(), floor.getName());
				}
				logger.info(String.format(LogTemplate.QUERY_FLOOR_OK, device, floors.size()));
			}
			return retModel;
		} else {
			logger.warn(String.format(LogTemplate.INVALID_PARAMS, params));
			return new BaseDTO(ErrorCode.INVALID_REQ);
		}
	}

	@Override
	public BaseDTO queryStatus(Map<String, String> params) {
		String device = params.get("device");
		String floor = params.get("floor");
		String type = params.get("type");
		if (!StringUtil.isBlank(device) && floor != null && type != null) {
			StatusDTO retModel = new StatusDTO();
			// 查询出所有房间
			IRoomDao roomDao = (IRoomDao) BaseFactory.getInstance(IRoomDao.class);
			List<RoomPO> rooms = roomDao.retrieveAllRoom();
			if (rooms != null) {
				// 检查过滤条件
				if (!floor.matches("\"\"||[\\d\\|]+") || !type.matches("\"\"||[\\d\\|]+")) {
					logger.warn(String.format(LogTemplate.QUERY_STATUS_INVALID_FILTER, device,
							floor, type));
					return new BaseDTO(ErrorCode.QUERY_STATUS_INVALID_FILTER);
				}
				// 分析过滤条件
				List<Integer> floorList = new ArrayList<Integer>();
				List<Integer> typeList = new ArrayList<Integer>();
				StringTokenizer st = new StringTokenizer(floor, "\\|");
				while (st.hasMoreTokens()) {
					floorList.add(Integer.parseInt(st.nextToken()));
				}
				st = new StringTokenizer(type, "\\|");
				while (st.hasMoreTokens()) {
					typeList.add(Integer.parseInt(st.nextToken()));
				}
				// 分析在住情况
				ICheckinDao checkinDao = (ICheckinDao) BaseFactory.getInstance(ICheckinDao.class);
				List<Object> stayList = checkinDao.retrieveAllCheckinIdWithStayFlag();
				// 筛选房间
				for (RoomPO room : rooms) {
					if (floorList.size() != 0 && !floorList.contains(room.getFloor())) {
						// 不符合楼层过滤条件
						retModel.addStatus(room.getId(), room.getName(), room.getFloor(),
								room.getType(), 1);
					} else if (typeList.size() != 0 && !typeList.contains(room.getType())) {
						// 不符合房型过滤条件
						retModel.addStatus(room.getId(), room.getName(), room.getFloor(),
								room.getType(), 1);
					} else if (stayList.contains(room.getId())) {
						// 当前在住
						retModel.addStatus(room.getId(), room.getName(), room.getFloor(),
								room.getType(), 2);
					} else {
						// 符合条件
						retModel.addStatus(room.getId(), room.getName(), room.getFloor(),
								room.getType(), 0);
					}
				}
				logger.info(String.format(LogTemplate.QUERY_STATUS_OK, device, rooms.size()));
			}
			return retModel;
		} else {
			logger.warn(String.format(LogTemplate.INVALID_PARAMS, params));
			return new BaseDTO(ErrorCode.INVALID_REQ);
		}
	}

	@Override
	public BaseDTO queryRoom(Map<String, String> params) {
		String device = params.get("device");
		String cardid = params.get("cardid");
		if (!StringUtil.isBlank(device) && !StringUtil.isBlank(cardid)) {
			RoomDTO retModel = new RoomDTO();
			// 房间信息
			IRoomDao roomDao = (IRoomDao) BaseFactory.getInstance(IRoomDao.class);
			RoomPO room = roomDao.retrieveRoomByCardId(cardid);
			if (room != null) {
				retModel.setId(room.getId());
				retModel.setName(room.getName());
			} else {
				logger.warn(String.format(LogTemplate.QUERY_ROOM_NO_SUCH_CARD, device, cardid));
				return new BaseDTO(ErrorCode.QUERY_ROOM_NO_SUCH_CARD);
			}
			// 房型信息
			ITypeDao typeDao = (ITypeDao) BaseFactory.getInstance(ITypeDao.class);
			TypePO type = typeDao.retrieveTypeById(room.getType());
			if (type != null) {
				retModel.setType(type.getName());
			}
			// 入住信息
			ICheckinDao checkinDao = (ICheckinDao) BaseFactory.getInstance(ICheckinDao.class);
			CheckinPO checkin = checkinDao.retrieveCheckinByRoom(room.getId());
			if (checkin != null) {
				retModel.setCheckin(checkin.getCheckin());
				retModel.setCheckout(checkin.getCheckout());
			} else {
				logger.warn(String.format(LogTemplate.QUERY_ROOM_NO_CHECK_IN, device, cardid));
				return new BaseDTO(ErrorCode.QUERY_ROOM_NO_CHECK_IN);
			}
			// 入住者信息
			if (checkin != null) {
				if (checkin.getMember() != null) {
					IMemberDao memberDao = (IMemberDao) BaseFactory.getInstance(IMemberDao.class);
					MemberPO member = memberDao.retrieveMemberById(checkin.getMember());
					retModel.setMobile(StringUtil.shieldPartionStr(member.getMobile(), 3, 6));
				} else if (checkin.getGuest() != null) {
					IGuestDao guestDao = (IGuestDao) BaseFactory.getInstance(IGuestDao.class);
					GuestPO guest = guestDao.retrieveGuestById(checkin.getGuest());
					retModel.setMobile(StringUtil.shieldPartionStr(guest.getMobile(), 3, 6));
				}
			}
			logger.info(String.format(LogTemplate.QUERY_ROOM_OK, device, cardid));
			return retModel;
		} else {
			logger.warn(String.format(LogTemplate.INVALID_PARAMS, params));
			return new BaseDTO(ErrorCode.INVALID_REQ);
		}
	}

	@Override
	public BaseDTO queryInfo(Map<String, String> params) {
		String device = params.get("device");
		String type = params.get("type");
		if (!StringUtil.isBlank(device) && !StringUtil.isBlank(type)) {
			switch (type) {
			case "basic":
				IConfService confServ = (IConfService) BaseFactory.getInstance(IConfService.class);
				Map<String, String> content = new HashMap<String, String>();
				content.put("name", confServ.getConf("name"));
				content.put("address", confServ.getConf("address"));
				content.put("telephone", confServ.getConf("telephone"));
				content.put("notice", confServ.getConf("notice"));

				InfoDTO retModel = new InfoDTO();
				retModel.setContent(content);
				logger.info(String.format(LogTemplate.QUERY_INFO_OK, device, type));
				return retModel;
			default:
				logger.warn(String.format(LogTemplate.QUERY_INFO_NO_SUCH_TYPE, device, type));
				return new BaseDTO(ErrorCode.QUERY_INFO_NO_SUCH_TYPE);
			}
		} else {
			logger.warn(String.format(LogTemplate.INVALID_PARAMS, params));
			return new BaseDTO(ErrorCode.INVALID_REQ);
		}
	}

}
